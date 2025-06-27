import os
import glob
import argparse
import requests

def main():
    parser = argparse.ArgumentParser(description="Translate properties files.")
    parser.add_argument('--src-dir', default=os.environ.get('SRC_DIR', 'common/src/main/resources'), help='Source directory for properties files')
    parser.add_argument('--src-lang', default='en', help='Source language code')
    parser.add_argument('--tgt-lang', required=True, help='Target language code')
    args = parser.parse_args()

    TRANSLATE_URL = "https://libretranslate.de/translate"

    pattern = os.path.join(args.src_dir, f"*_en.properties")
    for path in glob.glob(pattern):
        tgt_path = path.replace("_en.properties", f"_{args.tgt_lang}.properties")
        out_lines = []
        with open(path, encoding="utf-8") as src:
            for line in src:
                if "=" in line and not line.strip().startswith("#"):
                    k, v = line.split("=", 1)
                    resp = requests.post(TRANSLATE_URL, data={
                        "q": v.strip(),
                        "source": args.src_lang,
                        "target": args.tgt_lang,
                        "format": "text"
                    })
                    if resp.ok:
                        v_trans = resp.json().get("translatedText", v.strip())
                        out_lines.append(f"{k}={v_trans}\n")
                    else:
                        out_lines.append(line)
                else:
                    out_lines.append(line)
        with open(tgt_path, "w", encoding="utf-8") as tgt:
            tgt.writelines(out_lines)

        print(f"Translated {path} to {tgt_path}")

if __name__ == "__main__":
    main()
