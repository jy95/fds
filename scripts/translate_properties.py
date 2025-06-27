import os
import glob
import argparse
import javaproperties
import argostranslate.package
import argostranslate.translate
import re

# --- Expressions régulières pour protéger ce qui ne doit pas être traduit ---
ICU_BLOCK_PATTERN = re.compile(r'\{[^{}\s,]+,\s*(plural|select|choice),[^{}]*\{[^{}]*\}[^{}]*\}')
PLACEHOLDER_PATTERN = re.compile(r'\{[\w\d_.]+\}')
ESCAPED_CHAR_PATTERN = re.compile(r'\\[=: <>]')

def ensure_model_installed(src_lang, tgt_lang):
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    package_to_install = next(
        (p for p in available_packages if p.from_code == src_lang and p.to_code == tgt_lang),
        None
    )
    if package_to_install:
        argostranslate.package.install_from_path(package_to_install.download())

def protect_translatable_parts(text):
    preserved = {}

    def replace(match):
        token = f"__P{len(preserved)}__"
        preserved[token] = match.group(0)
        return token

    # Ordre important
    text = ICU_BLOCK_PATTERN.sub(replace, text)
    text = PLACEHOLDER_PATTERN.sub(replace, text)
    text = ESCAPED_CHAR_PATTERN.sub(replace, text)

    return text, preserved

def restore_preserved_parts(text, preserved):
    for token, original in preserved.items():
        text = text.replace(token, original)
    return text

def translate_value(value, src_lang, tgt_lang):
    protected_text, preserved = protect_translatable_parts(value)
    translated = argostranslate.translate.translate(protected_text, src_lang, tgt_lang)
    return restore_preserved_parts(translated, preserved)

def main():
    parser = argparse.ArgumentParser(description="Translate .properties files using Argos Translate.")
    parser.add_argument('--src-dir', default=os.environ.get('SRC_DIR', 'common/src/main/resources'), help='Source directory containing *_en.properties files')
    parser.add_argument('--src-lang', default='en', help='Source language code (default: en)')
    parser.add_argument('--tgt-lang', required=True, help='Target language code (e.g., fr, es)')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)

    pattern = os.path.join(args.src_dir, "*_en.properties")
    for path in glob.glob(pattern):
        tgt_path = path.replace("_en.properties", f"_{args.tgt_lang}.properties")

        with open(path, "r", encoding="utf-8") as f:
            props = javaproperties.load(f, object_pairs_hook=dict)

            translated_props = {}
            for key, val in props.items():
                translated_val = translate_value(val, args.src_lang, args.tgt_lang)
                translated_props[key] = translated_val
    
            with open(tgt_path, "w", encoding="utf-8") as f:
                javaproperties.dump(translated_props, f)

if __name__ == "__main__":
    main()
