import os
import glob
import argparse
import argostranslate.package
import argostranslate.translate
import urllib.request

MODEL_BASE_URL = "https://argosopentech.nyc3.digitaloceanspaces.com/argospm/packages"

def ensure_model_installed(src_lang, tgt_lang):
    installed_languages = argostranslate.translate.get_installed_languages()
    from_lang = next((lang for lang in installed_languages if lang.code == src_lang), None)
    to_lang = next((lang for lang in installed_languages if lang.code == tgt_lang), None)

    if from_lang and to_lang:
        return

    # Download and install model
    model_filename = f"{src_lang}_{tgt_lang}.argosmodel"
    model_url = f"{MODEL_BASE_URL}/{model_filename}"
    local_path = os.path.join("/tmp", model_filename)

    print(f"Téléchargement du modèle de traduction {src_lang}->{tgt_lang}...")
    urllib.request.urlretrieve(model_url, local_path)
    argostranslate.package.install_from_path(local_path)

def get_translator(src_lang, tgt_lang):
    installed_languages = argostranslate.translate.get_installed_languages()
    from_lang = next(lang for lang in installed_languages if lang.code == src_lang)
    to_lang = next(lang for lang in installed_languages if lang.code == tgt_lang)
    return from_lang.get_translation(to_lang)

def main():
    parser = argparse.ArgumentParser(description="Translate .properties files using Argos Translate.")
    parser.add_argument('--src-dir', default=os.environ.get('SRC_DIR', 'common/src/main/resources'), help='Source directory')
    parser.add_argument('--src-lang', default='en', help='Source language code')
    parser.add_argument('--tgt-lang', required=True, help='Target language code')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)
    translator = get_translator(args.src_lang, args.tgt_lang)

    pattern = os.path.join(args.src_dir, f"*_en.properties")
    for path in glob.glob(pattern):
        tgt_path = path.replace("_en.properties", f"_{args.tgt_lang}.properties")
        out_lines = []
        with open(path, encoding="utf-8") as src:
            for line in src:
                if "=" in line and not line.strip().startswith("#"):
                    k, v = line.split("=", 1)
                    v_trans = translator.translate(v.strip())
                    out_lines.append(f"{k}={v_trans}\n")
                else:
                    out_lines.append(line)
        with open(tgt_path, "w", encoding="utf-8") as tgt:
            tgt.writelines(out_lines)
        print(f"Traduit : {path} -> {tgt_path}")

if __name__ == "__main__":
    main()
