import os
import re
import argparse
import argostranslate.package
import argostranslate.translate


def ensure_model_installed(src_lang, tgt_lang):
    print(f"Checking/installing Argos model for {src_lang} -> {tgt_lang}...")
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    package = next((p for p in available_packages if p.from_code == src_lang and p.to_code == tgt_lang), None)
    if package:
        argostranslate.package.install_from_path(package.download())
        print(f"Installed model for {src_lang} -> {tgt_lang}")
    else:
        print(f"No model found for {src_lang} -> {tgt_lang}")


def translate_value(text, src_lang, tgt_lang):
    return argostranslate.translate.translate(text, src_lang, tgt_lang)


def update_localeproviderbase(localeprovider_path, tgt_lang):
    try:
        with open(localeprovider_path, "r", encoding="utf-8") as f:
            lines = f.readlines()
    except FileNotFoundError:
        print(f"File not found: {localeprovider_path}")
        return

    new_locale = f'Locale.forLanguageTag("{tgt_lang}")'
    if any(new_locale in line for line in lines):
        print(f"Locale {tgt_lang} already present in {localeprovider_path}")
        return

    # Find end of Stream.of(...), insert before closing `)`
    for i, line in enumerate(lines):
        if "Stream.of" in line:
            start_idx = i
            break
    else:
        print("Stream.of(...) not found!")
        return

    # Find the closing parenthesis line of Stream.of(...)
    for j in range(start_idx + 1, len(lines)):
        if ")" in lines[j]:
            indent = re.match(r"(\s*)", lines[j]).group(1)
            lines.insert(j, f"{indent}{new_locale},\n")
            with open(localeprovider_path, "w", encoding="utf-8") as f:
                f.writelines(lines)
            print(f"✅ Added {new_locale} to {localeprovider_path}")
            return

    print("Could not find closing ')' of Stream.of(...)")


def process_java_test_files(test_dir, tgt_lang, src_lang="en"):
    english_string_pattern = re.compile(
        r'^(\s*)if\s*\(\s*locale\.equals\(Locale\.ENGLISH\)\s*\)\s*\{\s*return\s*"((?:\\.|[^"\\])*)";\s*\}',
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
            for match in reversed(matches):  # reversed = safe offsets
                indent = match.group(1)
                eng_text = match.group(2)
                translated = translate_value(eng_text, src_lang, tgt_lang)

                new_block = (
                    f'\n{indent}else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                    f'{indent}    return "{translated}";\n'
                    f'{indent}}}'
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
    parser.add_argument('--localeprovider-path', default="common/src/test/java/io/github/jy95/fds/utilities/LocaleProviderBase.java", help='Path to LocaleProviderBase.java')
    parser.add_argument('--src-lang', default="en", help='Source language code (default: en)')
    parser.add_argument('--tgt-lang', required=True, help='Target language code (e.g., fr, es)')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)
    process_java_test_files(args.test_dir, args.tgt_lang, src_lang=args.src_lang)
    update_localeproviderbase(args.localeprovider_path, args.tgt_lang)


if __name__ == "__main__":
    main()
