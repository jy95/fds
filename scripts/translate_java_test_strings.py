import os
import argparse
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava
import argostranslate.package
import argostranslate.translate

# Initialize Tree-sitter Java
JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser(JAVA_LANGUAGE)

def ensure_model_installed(src_lang, tgt_lang):
    print(f"Checking Argos model: {src_lang} -> {tgt_lang}...")
    try:
        argostranslate.package.update_package_index()
    except: pass
    available = argostranslate.package.get_available_packages()
    package = next((p for p in available if p.from_code == src_lang and p.to_code == tgt_lang), None)
    if package:
        argostranslate.package.install_from_path(package.download())

def translate_value(text, src_lang, tgt_lang):
    translated = argostranslate.translate.translate(text, src_lang, tgt_lang)
    return translated.replace('\\', '\\\\').replace('"', '\\"')

def get_text(node, source_code):
    return source_code[node.start_byte:node.end_byte].decode('utf-8')

def process_java_file(path, tgt_lang, src_lang="en"):
    with open(path, "rb") as f:
        source_code = f.read()
    
    # Check if already translated
    if f'Locale.forLanguageTag("{tgt_lang}")'.encode() in source_code:
        return

    tree = parser.parse(source_code)
    root_node = tree.root_node

    # Query to find the English if-statement and the English return string
    # This finds an 'if' where the condition contains 'Locale.ENGLISH'
    query = JAVA_LANGUAGE.query("""
        (if_statement
            condition: (parenthesized_expression (method_invocation)) @cond
            consequence: (block (return_statement (string_literal) @eng_str))
        ) @if_stmt
    """)

    captures = query.captures(root_node)
    
    # Filter for the correct English block
    matches = []
    for node, tag in captures:
        if tag == "if_stmt":
            cond_text = get_text(node.child_by_field_name('condition'), source_code)
            if "Locale.ENGLISH" in cond_text:
                # Find the corresponding string_literal for this specific if_stmt
                eng_str = None
                for n, t in captures:
                    if t == "eng_str" and n.start_byte > node.start_byte and n.end_byte < node.end_byte:
                        eng_str = get_text(n, source_code).strip('"')
                        break
                
                if eng_str:
                    matches.append((node, eng_str))

    if not matches:
        return

    new_source = source_code.decode('utf-8')
    # Process in reverse to keep byte offsets valid
    for if_node, eng_text in reversed(matches):
        # Follow the else chain
        current = if_node
        while True:
            # Tree-sitter structure: if_stmt -> alternative -> if_stmt (for else if)
            alt = current.child_by_field_name('alternative')
            if alt and alt.type == 'if_statement':
                current = alt
            else:
                break
        
        # 'current' is now the last 'else if' or the primary 'if'
        # We look for the 'else' keyword or the start of the final 'block' (the else block)
        final_else = current.child_by_field_name('alternative')
        if final_else and final_else.type == 'block':
            # Found the final 'else { ... }'
            insert_pos = final_else.start_byte
            
            # Detect indentation
            lines = new_source[:insert_pos].splitlines()
            indent = ""
            if lines:
                last_line = lines[-1]
                indent = last_line[:len(last_line) - len(last_line.lstrip())]

            translated = translate_value(eng_text, src_lang, tgt_lang)
            
            insertion = (
                f'else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                f'{indent}    return "{translated}";\n'
                f'{indent}}} '
            )
            
            # Reconstruct string
            new_source = new_source[:insert_pos] + insertion + new_source[insert_pos:]

    with open(path, "w", encoding="utf-8") as f:
        f.write(new_source)
    print(f"✅ Tree-sitter Updated: {path}")

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--test-dir', default="common/src/test/java/io/github/jy95/fds")
    parser.add_argument('--src-lang', default="en")
    parser.add_argument('--tgt-lang', required=True)
    args = parser.parse_args()

    ensure_model_installed(args.src_lang, args.tgt_lang)

    for root, _, files in os.walk(args.test_dir):
        for f in files:
            if f.endswith(".java"):
                process_java_file(os.path.join(root, f), args.tgt_lang, args.src_lang)

if __name__ == "__main__":
    main()