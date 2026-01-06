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
    if len(s) >= 2 and ((s[0] == '"' and s[-1] == '"') or (s[0] == "'" and s[-1] == "'")):
        return s[1:-1]
    return s

def collect_switch_expressions(root_node):
    """Finds all switch expressions in the file."""
    result = []
    stack = [root_node]
    while stack:
        node = stack.pop()
        if node.type == 'switch_expression':
            result.append(node)
        stack.extend(node.children)
    return result

def process_java_file(path, tgt_lang, src_lang="en"):
    try:
        with open(path, "rb") as f:
            source_bytes = f.read()

        # Idempotency check: Don't add if the tag already exists in a case
        if f'case "{tgt_lang}"'.encode() in source_bytes:
            return "SKIPPED_ALREADY_EXISTS"

        tree = parser.parse(source_bytes)
        source_text = source_bytes.decode('utf-8')
        switches = collect_switch_expressions(tree.root_node)

        if not switches:
            return "SKIPPED_NO_MATCH"

        # Process from bottom to top to preserve byte offsets
        switches.sort(key=lambda x: x.start_byte, reverse=True)
        updated = False

        for switch_node in switches:
            default_rule = None
            rules = [n for n in switch_node.children if n.type == 'switch_rule']
            
            # Find the default rule
            for rule in rules:
                # In tree-sitter-java, switch_rule has a switch_label child
                labels = [c for c in rule.children if c.type == 'switch_label']
                if labels and 'default' in get_node_text(labels[0], source_bytes):
                    default_rule = rule
                    break
            
            if not default_rule:
                continue

            # Extract the English string from the default rule (reference)
            # It usually looks like: default -> "English String"
            str_nodes = []
            stack = [default_rule]
            while stack:
                n = stack.pop()
                if n.type == 'string_literal':
                    str_nodes.append(n)
                stack.extend(n.children)
            
            if not str_nodes:
                continue
            
            # Use the last string literal in the default rule as source
            eng_text_raw = get_node_text(str_nodes[0], source_bytes)
            eng_text = strip_quotes_once(eng_text_raw)

            # Determine indentation based on the default rule's start
            line_start = source_text.rfind('\n', 0, default_rule.start_byte) + 1
            indent = source_text[line_start:default_rule.start_byte]
            # Ensure we only keep whitespace for indentation
            indent = indent[:len(indent) - len(indent.lstrip())]

            # Translate
            translated = translate_value(eng_text, src_lang, tgt_lang)
            
            # Create new case block
            new_case = f'case "{tgt_lang}" -> "{translated}";\n{indent}'
            
            # Inject before the default rule
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
                stats.setdefault(status, []).append(file_path)

    print("\n" + "=" * 50)
    print(f"DEBUG: Scanned {found_files} .java files")
    print(f"PROCESSING SUMMARY FOR: {args.tgt_lang}")
    print("=" * 50)
    print(f"✅ UPDATED: {len(stats.get('UPDATED', []))}")
    print(f"⏭️  ALREADY EXISTS: {len(stats.get('SKIPPED_ALREADY_EXISTS', []))}")
    print(f"⚪ NO TARGET PATTERN: {len(stats.get('SKIPPED_NO_MATCH', []))}")
    print(f"❌ ERRORS: {len(stats.get('ERROR', []))}")
    print("=" * 50)

if __name__ == "__main__":
    main()
