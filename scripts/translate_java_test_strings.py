import os
import glob
import argparse
import re
import argostranslate.package
import argostranslate.translate

def ensure_model_installed(src_lang, tgt_lang):
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    package_to_install = next(
        (p for p in available_packages if p.from_code == src_lang and p.to_code == tgt_lang),
        None
    )
    if package_to_install:
        argostranslate.package.install_from_path(package_to_install.download())

def translate_value(text, src_lang, tgt_lang):
    return argostranslate.translate.translate(text, src_lang, tgt_lang)

def update_localeproviderbase(localeprovider_path, tgt_lang):
    with open(localeprovider_path, "r", encoding="utf-8") as f:
        lines = f.readlines()

    new_locale_line = f'                        Locale.forLanguageTag("{tgt_lang}"),\n'
    if any(new_locale_line.strip() in l.strip() for l in lines):
        return  # Already present

    for i, line in enumerate(lines):
        if "Stream.of(" in line:
            insert_idx = i + 1
            break
    else:
        print("Stream.of( not found in LocaleProviderBase.java!")
        return

    lines.insert(insert_idx, new_locale_line)
    with open(localeprovider_path, "w", encoding="utf-8") as f:
        f.writelines(lines)
    print(f"Added Locale.forLanguageTag(\"{tgt_lang}\") to {localeprovider_path}")

def process_java_test_files(test_dir, tgt_lang, src_lang="en"):
    pattern = os.path.join(test_dir, "*.java")
    english_string_pattern = re.compile(
        r'if\s*\(\s*locale\.equals\(Locale\.ENGLISH\)\s*\)\s*\{\s*return\s*"((?:\\.|[^"\\])*)";\s*\}',
        re.DOTALL
    )

    for path in glob.glob(pattern):
        with open(path, "r", encoding="utf-8") as f:
            content = f.read()

        matches = list(english_string_pattern.finditer(content))
        if not matches:
            continue

        for match in reversed(matches):  # reversed to not mess with offsets
            eng_text = match.group(1)
            translated_text = translate_value(eng_text, src_lang, tgt_lang)
            new_block = f'\n        else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n            return "{translated_text}";\n        }}'
            # Only insert if not already present
            if new_block not in content:
                insert_at = match.end()
                content = content[:insert_at] + new_block + content[insert_at:]

        with open(path, "w", encoding="utf-8") as f:
            f.write(content)
        print(f"Updated {path}")

def main():
    parser = argparse.ArgumentParser(description="Translate Java test strings using Argos Translate and update test files and LocaleProviderBase.")
    parser.add_argument('--test-dir', default="common/src/test/java/io/github/jy95/fds", help='Directory containing test .java files')
    parser.add_argument('--localeprovider-path', default="common/src/test/java/io/github/jy95/fds/utilities/LocaleProviderBase.java", help='Path to LocaleProviderBase.java')
    parser.add_argument('--src-lang', default="en", help='Source language code (default: en)')
    parser.add_argument('--tgt-lang', required=True, help='Target language code (e.g., fr, es)')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)
    process_java_test_files(args.test_dir, args.tgt_lang, src_lang=args.src_lang)
    update_localeproviderbase(args.localeprovider_path, args.tgt_lang)

if __name__ == "__main__":
    main()
