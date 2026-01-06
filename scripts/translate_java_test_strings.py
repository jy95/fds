import os
import argparse
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava
import argostranslate.package
import argostranslate.translate

# Initialization for Tree-sitter
JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser(JAVA_LANGUAGE)

def ensure_model_installed(src_lang, tgt_lang):
    print(f"INFO: Checking Argos model: {src_lang} -> {tgt_lang}...")
    try:
        argostranslate.package.update_package_index()
    except Exception as e:
        print(f"WARNING: Could not update index: {e}")

    available = argostranslate.package.get_available_packages()
    package = next((p for p in available if p.from_code == src_lang and p.to_code == tgt_lang), None)
    if package:
        argostranslate.package.install_from_path(package.download())
    else:
        print(f"ERROR: Model not found for {src_lang} -> {tgt_lang}")

def translate_value(text, src_lang, tgt_lang):
    translated = argostranslate.translate.translate(text, src_lang, tgt_lang)
    return translated.replace('\\', '\\\\').replace('"', '\\"')

def get_node_text(node, source_bytes):
    return source_bytes[node.start_byte:node.end_byte].decode('utf-8')

def strip_quotes_once(s):
    s = s.strip()
    if len(s) >= 2 and ((s[0] == '"' and s[-1] == '"') or (s[0] == "'" and s[-1] == "'")):
        return s[1:-1]
    return s

def collect_switch_expressions(root_node):
    result = []
    stack = [root_node]
    while stack:
        node = stack.pop()
        # Check for both switch_expression (return switch) and switch_statement
        if node.type in ('switch_expression', 'switch_block'):
            result.append(node)
        for child in reversed(node.children):
            stack.append(child)
    return result

def process_java_file(path, tgt_lang, src_lang="en"):
    try:
        with open(path, "rb") as f:
            source_bytes = f.read()

        if f'case "{tgt_lang}"'.encode() in source_bytes:
            return "SKIPPED_ALREADY_EXISTS"

        tree = parser.parse(source_bytes)
        source_text = source_bytes.decode('utf-8')
        
        # Look for switch expressions or blocks
        switches = collect_switch_expressions(tree.root_node)
        if not switches:
            return "SKIPPED_NO_MATCH"

        switches.sort(key=lambda x: x.start_byte, reverse=True)
        updated = False

        for switch_node in switches:
            default_rule = None
            # Find the switch_rule that contains the 'default' label
            for child in switch_node.children:
                if child.type == 'switch_rule':
                    # Check labels within the rule
                    labels = [c for c in child.children if c.type == 'switch_label']
                    for l in labels:
                        if 'default' in get_node_text(l, source_bytes):
                            default_rule = child
                            break
                if default_rule: break
            
            if not default_rule:
                continue

            # Find the string literal on the right side of the arrow ->
            eng_text = None
            for child in reversed(default_rule.children):
                if child.type == 'string_literal':
                    eng_text = strip_quotes_once(get_node_text(child, source_bytes))
                    break
                # Handle cases where the string is inside a block or expression
                if child.type in ('expression_statement', 'block'):
                    # Deep search for the first string literal
                    curr_stack = [child]
                    while curr_stack:
                        curr = curr_stack.pop()
                        if curr.type == 'string_literal':
                            eng_text = strip_quotes_once(get_node_text(curr, source_bytes))
                            break
                        curr_stack.extend(reversed(curr.children))
                    if eng_text: break

            if not eng_text:
                continue

            # Get indentation
            line_start = source_text.rfind('\n', 0, default_rule.start_byte) + 1
            full_line = source_text[line_start:default_rule.start_byte]
            indent = full_line[:len(full_line) - len(full_line.lstrip())]

            translated = translate_value(eng_text, src_lang, tgt_lang)
            new_case = f'case "{tgt_lang}" -> "{translated}";\n{indent}'
            
            insert_pos = default_rule.start_byte
            source_text = source_text[:insert_pos] + new_case + source_text[insert_pos:]
            updated = True

        if updated:
            with open(path, "w", encoding="utf-8") as f:
                f.write(source_text)
            return "UPDATED"
        
        return "SKIPPED_NO_MATCH"

    except Exception as e:
        print(f"ERROR: Error in {path}: {e}")
        return "ERROR"

def main():
    argp = argparse.ArgumentParser()
    argp.add_argument('--test-dir', default="common/src/test/java/io/github/jy95/fds")
    argp.add_argument('--src-lang', default="en")
    argp.add_argument('--tgt-lang', required=True)
    args = argp.parse_args()

    if not os.path.exists(args.test_dir):
        print(f"ERROR: Directory NOT FOUND: {args.test_dir}")
        return

    ensure_model_installed(args.src_lang, args.tgt_lang)

    stats = {"UPDATED": [], "SKIPPED_ALREADY_EXISTS": [], "SKIPPED_NO_MATCH": [], "ERROR": []}
    found_files = 0

    for root, _, files in os.walk(args.test_dir):
        for f in files:
            if f.endswith(".java"):
                found_files += 1
                file_path = os.path.join(root, f)
                status = process_java_file(file_path, args.tgt_lang, args.src_lang)
                stats[status].append(file_path)

    print("\n" + "=" * 50)
    print(f"PROCESSING SUMMARY FOR: {args.tgt_lang}")
    print("=" * 50)
    print(f"✅ UPDATED: {len(stats['UPDATED'])}")
    print(f"⏭️  ALREADY EXISTS: {len(stats['SKIPPED_ALREADY_EXISTS'])}")
    print(f"⚪ NO TARGET PATTERN: {len(stats['SKIPPED_NO_MATCH'])}")
    print(f"❌ ERRORS: {len(stats['ERROR'])}")
    print("=" * 50)

if __name__ == "__main__":
    main()
