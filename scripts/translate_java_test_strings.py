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
    # Uses [^}]* and [^)]* to prevent exponential backtracking.
    # Group 1: Everything from the English 'if' up to the point of insertion.
    # Group 2: The 'else {' anchor where we insert the new code.
    chain_pattern = re.compile(
        r'(if\s*\(\s*locale\.equals\(\s*Locale\.ENGLISH\s*\)\s*\{'  # Start of English if
        r'[^}]*\}'                                                 # Inside English block
        r'(?:\s*else\s+if\s*\([^)]*\)\s*\{[^}]*\})*'               # Previous else-if blocks
        r'\s*)(else\s*\{)',                                        # Final else anchor
        re.DOTALL
    )

    # Specific regex to grab the English string safely
    eng_text_regex = re.compile(
        r'Locale\.ENGLISH\s*\)\s*\{\s*return\s*"([^"\\]*(?:\\.[^"\\]*)*)"', 
        re.DOTALL
    )

    for root, _, files in os.walk(test_dir):
        for filename in files:
            if not filename.endswith(".java"):
                continue

            path = os.path.join(root, filename)
            with open(path, "r", encoding="utf-8") as f:
                content = f.read()

            # Skip if the language tag already exists to avoid double-insertion
            if f'Locale.forLanguageTag("{tgt_lang}")' in content:
                continue

            matches = list(chain_pattern.finditer(content))
            if not matches:
                continue

            updated = False
            # Process in reverse to maintain valid string offsets as we modify content
            for match in reversed(matches):
                full_chain_prefix = match.group(1) 
                
                eng_match = eng_text_regex.search(full_chain_prefix)
                if not eng_match:
                    continue
                
                eng_text = eng_match.group(1)
                translated = translate_value(eng_text, src_lang, tgt_lang)

                # Find indentation of the final 'else' to match the style
                lines_before = content[:match.start(2)].splitlines()
                indent = ""
                if lines_before:
                    last_line = lines_before[-1]
                    indent = last_line[:len(last_line) - len(last_line.lstrip())]

                # Construct the new code block
                new_block = (
                    f'else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                    f'{indent}    return "{translated}";\n'
                    f'{indent}}} '
                )

                # Insert right before the final 'else'
                insert_at = match.start(2)
                content = content[:insert_at] + new_block + content[insert_at:]
                updated = True

            if updated:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(content)
                print(f"✅ Updated: {path}")

def main():
    parser = argparse.ArgumentParser(
        description="Securely translate Java test strings and insert locale before the final else block."
    )
    parser.add_argument('--test-dir', default="common/src/test/java/io/github/jy95/fds", help='Root directory for Java test files')
    parser.add_argument('--src-lang', default="en", help='Source language code (default: en)')
    parser.add_argument('--tgt-lang', required=True, help='Target language code (e.g., fr, es, it)')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)
    process_java_test_files(args.test_dir, args.tgt_lang, src_lang=args.src_lang)

if __name__ == "__main__":
    main()
