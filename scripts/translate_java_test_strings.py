import os
import re
import argparse
import argostranslate.package
import argostranslate.translate

def ensure_model_installed(src_lang, tgt_lang):
    print(f"Checking/installing Argos model for {src_lang} -> {tgt_lang}...")
    try:
        argostranslate.package.update_package_index()
    except Exception as e:
        print(f"Note: Could not update package index ({e}), attempting to use local cache.")
        
    available_packages = argostranslate.package.get_available_packages()
    package = next((p for p in available_packages if p.from_code == src_lang and p.to_code == tgt_lang), None)
    if package:
        argostranslate.package.install_from_path(package.download())
        print(f"✅ Installed model for {src_lang} -> {tgt_lang}")
    else:
        print(f"❌ No model found for {src_lang} -> {tgt_lang}")

def translate_value(text, src_lang, tgt_lang):
    translated = argostranslate.translate.translate(text, src_lang, tgt_lang)
    # Escape backslashes and double quotes for Java compatibility
    return translated.replace('\\', '\\\\').replace('"', '\\"')

def process_java_test_files(test_dir, tgt_lang, src_lang="en"):
    # Updated Regex: Handles flexible whitespace/newlines and capture groups for indentation
    # Group 1: Base Indentation
    # Group 2: Potential extra indentation inside the block
    # Group 3: The English string content
    english_string_pattern = re.compile(
        r'^(\s*)if\s*\(\s*locale\.equals\(\s*Locale\.ENGLISH\s*\)\s*\)\s*\{(\s*)return\s*"((?:\\.|[^"\\])*)";\s*\}',
        re.MULTILINE
    )

    for root, _, files in os.walk(test_dir):
        for filename in files:
            if not filename.endswith(".java"):
                continue

            path = os.path.join(root, filename)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()

            matches = list(english_string_pattern.finditer(content))
            if not matches:
                continue

            updated = False
            # Process in reverse to maintain valid string offsets
            for match in reversed(matches):
                base_indent = match.group(1)   # Indentation of the 'if'
                inner_indent = match.group(2)  # Indentation before 'return' (preserves tabs/spaces)
                eng_text = match.group(3)
                
                translated = translate_value(eng_text, src_lang, tgt_lang)

                # Construct the new block using detected indentation
                new_block = (
                    f'\n{base_indent}else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{{inner_indent}'
                    f'return "{translated}";\n'
                    f'{base_indent}}}'
                )

                if f'Locale.forLanguageTag("{tgt_lang}")' not in content:
                    insert_at = match.end()
                    content = content[:insert_at] + new_block + content[insert_at:]
                    updated = True

            if updated:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(content)
                print(f"✅ Updated: {path}")

def main():
    parser = argparse.ArgumentParser(
        description="Translate Java test strings using Argos Translate and update LocaleProviderBase."
    )
    parser.add_argument('--test-dir', default="common/src/test/java/io/github/jy95/fds", help='Root directory for Java test files')
    parser.add_argument('--src-lang', default="en", help='Source language code (default: en)')
    parser.add_argument('--tgt-lang', required=True, help='Target language code (e.g., fr, es)')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)
    process_java_test_files(args.test_dir, args.tgt_lang, src_lang=args.src_lang)

if __name__ == "__main__":
    main()
