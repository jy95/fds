import os
import argparse
from tree_sitter import Language, Parser
import tree_sitter_java as tsjava
import argostranslate.package
import argostranslate.translate

# Initialization for Tree-sitter (adapt if you use a built .so)
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
    # escape backslashes and quotes for Java string literal
    return translated.replace('\\', '\\\\').replace('"', '\\"')


def get_node_text(node, source_bytes):
    return source_bytes[node.start_byte:node.end_byte].decode('utf-8')


def collect_if_statements(root_node):
    result = []
    stack = [root_node]
    while stack:
        node = stack.pop()
        if node.type == 'if_statement':
            result.append(node)
        stack.extend(node.children)
    return result


def collect_return_string_literals(root_node):
    result = []
    stack = [(root_node, [])]  # (node, ancestors_list)
    while stack:
        node, ancestors = stack.pop()
        if node.type == 'string_literal':
            if any(a.type == 'return_statement' for a in ancestors):
                result.append(node)
        for child in node.children:
            stack.append((child, ancestors + [node]))
    return result


def find_chain_root(if_node):
    node = if_node
    while True:
        p = getattr(node, 'parent', None)
        if not p or p.type != 'if_statement':
            break
        alt = p.child_by_field_name('alternative')
        if not alt:
            break
        if if_node.start_byte >= alt.start_byte and if_node.end_byte <= alt.end_byte:
            node = p
            continue
        break
    return node


def find_else_keyword_pos(source_text, alt_start):
    start_search = max(0, alt_start - 200)
    snippet = source_text[start_search:alt_start + 20]
    idx = snippet.rfind('else')
    if idx != -1:
        return start_search + idx
    return alt_start


def strip_quotes_once(s):
    if len(s) >= 2 and ((s[0] == '"' and s[-1] == '"') or (s[0] == "'" and s[-1] == "'")):
        return s[1:-1]
    return s


def gather_chain_nodes(root):
    """
    Return the list of if_statement nodes in the chain starting at root,
    traversing the 'alternative' field while it is an if_statement.
    """
    nodes = []
    cur = root
    while True:
        nodes.append(cur)
        alt = cur.child_by_field_name('alternative')
        if alt and alt.type == 'if_statement':
            cur = alt
            continue
        break
    return nodes


def process_java_file(path, tgt_lang, src_lang="en"):
    try:
        with open(path, "rb") as f:
            source_bytes = f.read()

        if f'Locale.forLanguageTag("{tgt_lang}")'.encode() in source_bytes:
            return "SKIPPED_ALREADY_EXISTS"

        tree = parser.parse(source_bytes)
        root_node = tree.root_node

        if_nodes = collect_if_statements(root_node)
        str_nodes = collect_return_string_literals(root_node)

        # Build unique chain roots
        roots = {}
        for if_node in if_nodes:
            root = find_chain_root(if_node)
            key = (root.start_byte, root.end_byte)
            roots[key] = root

        if not roots:
            return "SKIPPED_NO_MATCH"

        source_text = source_bytes.decode('utf-8')
        processed_roots = set()
        updated = False

        # For each if-else chain root, find the if that specifically checks Locale.ENGLISH
        for key, root in sorted(roots.items(), reverse=True):  # reverse so we process bottom-up
            if key in processed_roots:
                continue
            processed_roots.add(key)

            chain_nodes = gather_chain_nodes(root)

            # Prefer the if node whose condition contains 'Locale.ENGLISH'
            english_node = None
            for node in chain_nodes:
                cond_node = node.child_by_field_name('condition')
                if not cond_node:
                    # try to find parenthesized_expression inside the node
                    stack = [node]
                    found = None
                    while stack and not found:
                        n = stack.pop()
                        if n.type == 'parenthesized_expression':
                            found = n
                            break
                        stack.extend(n.children)
                    cond_node = found
                if not cond_node:
                    continue
                cond_text = get_node_text(cond_node, source_bytes)
                if 'Locale.ENGLISH' in cond_text:
                    english_node = node
                    break

            if not english_node:
                # If there's no explicit Locale.ENGLISH check, skip this chain
                continue

            # Find the return string literal that's inside english_node
            eng_text = None
            for s_node in str_nodes:
                if s_node.start_byte >= english_node.start_byte and s_node.end_byte <= english_node.end_byte:
                    eng_text = strip_quotes_once(get_node_text(s_node, source_bytes))
                    break

            if not eng_text:
                # No return string found for the English branch; skip
                continue

            # Now find where to insert the new else-if: before the final else (if present),
            # otherwise after the last if in the chain.
            # Walk down to the last if in the else-if chain
            current = root
            while True:
                alt = current.child_by_field_name('alternative')
                if alt and alt.type == 'if_statement':
                    current = alt
                else:
                    break

            final_else = current.child_by_field_name('alternative')
            if final_else:
                insert_pos = find_else_keyword_pos(source_text, final_else.start_byte)
                # Find start of the line containing insert_pos
                newline_pos = source_text.rfind('\n', 0, insert_pos)
                ws_start = newline_pos + 1 if newline_pos != -1 else 0
                # If the characters between ws_start and insert_pos are only spaces/tabs, remove them to avoid "}" + spaces + else
                between = source_text[ws_start:insert_pos]
                if all(c in ' \t' for c in between):
                    remove_start = ws_start
                else:
                    remove_start = insert_pos
                # Determine indentation from the whitespace at ws_start
                indent = ''
                if ws_start < len(source_text):
                    line = source_text[ws_start: source_text.find('\n', ws_start) if source_text.find('\n', ws_start) != -1 else len(source_text)]
                    indent = line[:len(line) - len(line.lstrip())]
                else:
                    indent = ''

                translated = translate_value(eng_text, src_lang, tgt_lang)
                new_code = (
                    f'{indent}else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                    f'{indent}    return "{translated}";\n'
                    f'{indent}}}\n'
                )

                # Replace the region [remove_start:insert_pos] with new_code
                source_text = source_text[:remove_start] + new_code + source_text[insert_pos:]
                updated = True

            else:
                # No else branch: insert after current.end_byte. Ensure proper newline separation.
                insert_pos = current.end_byte
                # Find if we are mid-line: check previous newline
                prev_newline = source_text.rfind('\n', 0, insert_pos)
                prev_char = source_text[insert_pos - 1] if insert_pos > 0 else '\n'
                # Determine indentation from the line where we're inserting (use current line indent)
                line_start = prev_newline + 1 if prev_newline != -1 else 0
                line = source_text[line_start: source_text.find('\n', line_start) if source_text.find('\n', line_start) != -1 else len(source_text)]
                indent = line[:len(line) - len(line.lstrip())]

                translated = translate_value(eng_text, src_lang, tgt_lang)
                new_code = (
                    f'\n{indent}else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                    f'{indent}    return "{translated}";\n'
                    f'{indent}}}\n'
                )

                source_text = source_text[:insert_pos] + new_code + source_text[insert_pos:]
                updated = True

        if updated:
            with open(path, "w", encoding="utf-8") as f:
                f.write(source_text)
            return "UPDATED"
        else:
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

    if stats.get('SKIPPED_NO_MATCH'):
        print("\nNO TARGET PATTERN:")
        for p in stats['SKIPPED_NO_MATCH']:
            print(f"- {p}")

    if stats.get('ERROR'):
        print("\nERRORS:")
        for p in stats['ERROR']:
            print(f"- {p}")


if __name__ == "__main__":
    main()