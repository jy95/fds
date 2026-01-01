import os
import glob
import argparse
import re
import argostranslate.package
import argostranslate.translate

# --- REGEX CONFIGURATION ---
# Captures the structure { "key", "value" }
BUNDLE_ENTRY_PATTERN = re.compile(r'(\{\s*"[^"]+"\s*,\s*")((?:[^"\\]|\\.)*)("\s*\})')
# Captures the class name to add suffix
CLASS_NAME_PATTERN = re.compile(r'public\s+class\s+([A-Za-z0-9_]+)')
# Captures Javadoc to update target language
JAVADOC_PATTERN = re.compile(r'\* (.*) \(([a-z]{2})\) resource bundle')
# Protection for ICU variables: {0}, {period}, {maxFrequency, plural, ...}
ICU_PATTERN = re.compile(r'\{[^{}]+\}')

class FHIRBundleTranslator:
    def __init__(self, src_lang='en', tgt_lang='es'):
        self.src = src_lang
        self.tgt = tgt_lang
        self._setup_translator()

    def _setup_translator(self):
        """Installs the translation model if not present."""
        argostranslate.package.update_package_index()
        available = argostranslate.package.get_available_packages()
        pkg = next((p for p in available if p.from_code == self.src and p.to_code == self.tgt), None)
        if pkg:
            print(f"--- Installing model {self.src} -> {self.tgt} ---")
            argostranslate.package.install_from_path(pkg.download())
        else:
            raise ValueError(f"Translation package not available for {self.src} -> {self.tgt}. "f"Available languages: {sorted(set(p.to_code for p in available if p.from_code == self.src))}")

    def process_text_content(self, text):
        """
        Handles common logic: translates text while protecting 
        ICU plural/select syntax and variables.
        """
        if not text.strip() or text.isdigit():
            return text
        
        # Masking variables to prevent translation (e.g., {0}, {minValue})
        preserved = {}
        def mask(m):
            token = f"V_VAR_{len(preserved)}_V"
            preserved[token] = m.group(0)
            return token
        
        masked_text = ICU_PATTERN.sub(mask, text)
        
        try:
            translated = argostranslate.translate.translate(masked_text, self.src, self.tgt)
        except Exception as e:
            print(f"Translation error: {e}")
            return text

        # Restore masked variables
        for token, original in preserved.items():
            translated = translated.replace(token, original)
        
        return translated

    def create_translated_java_class(self, source_path):
        """Generates the new Java class file with translated values."""
        filename = os.path.basename(source_path)
        is_comparator = "QuantityComparator" in filename
        
        with open(source_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # 1. Update Class name (Required for Java compilation)
        content = CLASS_NAME_PATTERN.sub(r'public class \1_' + self.tgt, content)
        
        # 2. Update Javadoc header
        content = JAVADOC_PATTERN.sub(rf'* \1 ({self.tgt}) resource bundle', content)

        # 3. Translate contents (unless it is a symbol-only file)
        if is_comparator:
            print(f"   [SKIP] {filename} (preserving mathematical symbols)")
        else:
            def replace_callback(match):
                prefix, val, suffix = match.groups()
                translated_val = self.process_text_content(val)
                return f"{prefix}{translated_val}{suffix}"
            
            content = BUNDLE_ENTRY_PATTERN.sub(replace_callback, content)

        # 4. Save to new file with language suffix
        new_filename = filename.replace(".java", f"_{self.tgt}.java")
        target_path = os.path.join(os.path.dirname(source_path), new_filename)

        if os.path.exists(target_path):
            print(f"   [WARN] {new_filename} already exists, overwriting...")
        
        with open(target_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"   [OK] Generated: {new_filename}")

def main():
    parser = argparse.ArgumentParser(description="FHIR Java ResourceBundle Translator")
    parser.add_argument('--tgt', required=True, help='Target language code (e.g., pt, es, hi, ar)')
    parser.add_argument('--dir', default='.', help='Root directory for Java files search')
    args = parser.parse_args()

    translator = FHIRBundleTranslator(src_lang='en', tgt_lang=args.tgt)

    # Search for base source files (ignoring already translated ones with '_')
    search_pattern = os.path.join(args.dir, "**/l10n/*.java")
    source_files = [f for f in glob.glob(search_pattern, recursive=True) 
                    if "_" not in os.path.basename(f) and "package-info" not in f]

    if not source_files:
        print("No source files found. Please check the --dir argument.")
        return

    for file_path in source_files:
        translator.create_translated_java_class(file_path)

if __name__ == "__main__":
    main()
