import os
import glob
import argparse
import argostranslate.package
import argostranslate.translate

def ensure_model_installed(src_lang, tgt_lang):
    argostranslate.package.update_package_index()
    available_packages = argostranslate.package.get_available_packages()
    package_to_install = next(
        filter(
            lambda x: x.from_code == src_lang and x.to_code == tgt_lang, available_packages
        )
    )
    argostranslate.package.install_from_path(package_to_install.download())

def main():
    parser = argparse.ArgumentParser(description="Translate .properties files using Argos Translate.")
    parser.add_argument('--src-dir', default=os.environ.get('SRC_DIR', 'common/src/main/resources'), help='Source directory')
    parser.add_argument('--src-lang', default='en', help='Source language code')
    parser.add_argument('--tgt-lang', required=True, help='Target language code')
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)

    pattern = os.path.join(args.src_dir, f"*_en.properties")
    for path in glob.glob(pattern):
        tgt_path = path.replace("_en.properties", f"_{args.tgt_lang}.properties")
        out_lines = []
        with open(path, encoding="utf-8") as src:
            for line in src:
                if "=" in line and not line.strip().startswith("#"):
                    k, v = line.split("=", 1)
                    v_trans = argostranslate.translate.translate(v.strip(), args.src_lang, args.tgt_lang)
                    out_lines.append(f"{k}={v_trans}\n")
                else:
                    out_lines.append(line)
        with open(tgt_path, "w", encoding="utf-8") as tgt:
            tgt.writelines(out_lines)
        print(f"Traduit : {path} -> {tgt_path}")

if __name__ == "__main__":
    main()
