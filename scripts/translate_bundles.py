import os
import glob
import argparse
import re
import argostranslate.package
import argostranslate.translate

# --- REGEX CONFIGURATION ---
# Captures the structure { "key", "value" } from the ListResourceBundle array
BUNDLE_ENTRY_PATTERN = re.compile(r'(\{\s*"[^"]+"\s*,\s*")((?:[^"\\]|\\.)*)("\s*\})')
# Captures the Java class name to append the language suffix
CLASS_NAME_PATTERN = re.compile(r'public\s+class\s+([A-Za-z0-9_]+)')
# Captures Javadoc to update target language metadata
JAVADOC_PATTERN = re.compile(r'\* (.*) \(([a-z]{2})\) resource bundle')

class FHIRBundleTranslator:
    def __init__(self, src_lang='en', tgt_lang='es'):
        self.src = src_lang
        self.tgt = tgt_lang
        self._setup_translator()

    def _setup_translator(self):
        """Installs the translation model if not present in the local system."""
        argostranslate.package.update_package_index()
        available = argostranslate.package.get_available_packages()
        pkg = next((p for p in available if p.from_code == self.src and p.to_code == self.tgt), None)
        if pkg:
            print(f"--- Installing model {self.src} -> {self.tgt} ---")
            argostranslate.package.install_from_path(pkg.download())
        else:
            raise ValueError(f"Translation package not available for {self.src} -> {self.tgt}.")

    def translate_segment(self, text):
        """Translates a plain text segment while ignoring technical keywords."""
        # Do not translate keywords used in ICU logic
        keywords = {'plural', 'select', 'choice', 'other', 'one', 'few', 'many', 'zero', 'two'}
        stripped = text.strip()
        
        if not stripped or stripped.lower() in keywords:
            return text
            
        try:
            # We translate only the non-technical parts
            translated = argostranslate.translate.translate(stripped, self.src, self.tgt)
            # Re-apply the original surrounding whitespace to preserve formatting
            return text.replace(stripped, translated)
        except Exception as e:
            print(f"   [ERROR] Translation failed for segment '{stripped}': {e}")
            return text

    def process_icu_message(self, text):
        """
        Recursively parses ICU strings to translate only the displayable text.
        Protects variables like {0} and structures like {var, plural, ...}.
        """
        if '{' not in text:
            return self.translate_segment(text)

        result = ""
        i = 0
        while i < len(text):
            if text[i] == '{':
                # Find matching closing brace to isolate the block
                stack = 1
                j = i + 1
                while j < len(text) and stack > 0:
                    if text[j] == '{': stack += 1
                    elif text[j] == '}': stack -= 1
                    j += 1
                
                inner = text[i+1:j-1]
                # Check if it's a complex block (contains type like plural or select)
                parts = inner.split(',', 2)
                
                if len(parts) < 2:
                    # Case: Simple variable like {0}, {unit} or {minValue}
                    # We keep it as is (Reserved FHIR/Java variables)
                    result += "{" + inner + "}"
                else:
                    # Case: Complex ICU block {variable, type, options}
                    var_name = parts[0].strip()
                    icu_type = parts[1].strip() # plural, select, choice
                    options_raw = parts[2]
                    
                    # Parse the options inside the block: key{content}
                    translated_options = ""
                    opt_i = 0
                    while opt_i < len(options_raw):
                        # Regex to find the next key and its starting brace
                        match = re.search(r'([^{}\s]+)\s*\{', options_raw[opt_i:])
                        if match:
                            key = match.group(1)
                            start_brace = opt_i + match.end() - 1
                            
                            # Find matching closing brace for the option content
                            opt_stack = 1
                            opt_j = start_brace + 1
                            while opt_j < len(options_raw) and opt_stack > 0:
                                if options_raw[opt_j] == '{': opt_stack += 1
                                elif options_raw[opt_j] == '}': opt_stack -= 1
                                opt_j += 1
                            
                            option_content = options_raw[start_brace+1:opt_j-1]
                            # Recursively process the content inside the option
                            translated_content = self.process_icu_message(option_content)
                            translated_options += f"{key}{{{translated_content}}} "
                            opt_i = opt_j
                        else:
                            opt_i += 1
                    
                    result += f"{{{var_name}, {icu_type}, {translated_options.strip()}}}"
                i = j
            else:
                # Process text found outside of any braces
                start_text = i
                while i < len(text) and text[i] != '{':
                    i += 1
                result += self.translate_segment(text[start_text:i])
        
        return result

    def create_translated_java_class(self, source_path):
        """Reads a Java ResourceBundle, translates its contents and writes a new file."""
        filename = os.path.basename(source_path)
        is_comparator = "QuantityComparator" in filename
        
        with open(source_path, 'r', encoding='utf-8') as f:
            content = f.read()

        # Update Class name and Javadoc language header
        content = CLASS_NAME_PATTERN.sub(r'public class \1_' + self.tgt, content)
        content = JAVADOC_PATTERN.sub(rf'* \1 ({self.tgt}) resource bundle', content)

        if is_comparator:
            print(f"   [SKIP] {filename} (preserving math symbols)")
        else:
            def replace_callback(match):
                prefix, val, suffix = match.groups()
                # Run the recursive parser to handle complex FHIR strings
                translated_val = self.process_icu_message(val)
                # Ensure Java double quotes are escaped if needed
                translated_val = translated_val.replace('"', '\\"')
                return f"{prefix}{translated_val}{suffix}"
            
            content = BUNDLE_ENTRY_PATTERN.sub(replace_callback, content)

        # Build new filename and target path
        new_filename = filename.replace(".java", f"_{self.tgt}.java")
        target_path = os.path.join(os.path.dirname(source_path), new_filename)
        
        with open(target_path, 'w', encoding='utf-8') as f:
            f.write(content)
        print(f"   [OK] Generated: {new_filename}")

def main():
    parser = argparse.ArgumentParser(description="FHIR Java ResourceBundle Translator")
    parser.add_argument('--tgt', required=True, help='Target language code (e.g., es, pt, de)')
    parser.add_argument('--dir', default=os.environ.get('SRC_DIR', '.'), help='Directory to search for Java files')
    args = parser.parse_args()

    # Initialize the translator logic
    translator = FHIRBundleTranslator(src_lang='en', tgt_lang=args.tgt)

    # Glob search for Java files in the l10n directory
    search_pattern = os.path.join(args.dir, "**/l10n/*.java")
    source_files = [f for f in glob.glob(search_pattern, recursive=True) 
                    if "_" not in os.path.basename(f) and "package-info" not in f]

    if not source_files:
        print("No source files found. Please check your path.")
        return

    for file_path in source_files:
        translator.create_translated_java_class(file_path)

if __name__ == "__main__":
    main()