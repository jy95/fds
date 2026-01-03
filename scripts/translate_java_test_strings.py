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
    """
    Return a list of nodes whose .type == 'if_statement'.
    """
    result = []
    stack = [root_node]
    while stack:
        node = stack.pop()
        if node.type == 'if_statement':
            result.append(node)
        stack.extend(node.children)
    return result


def collect_return_string_literals(root_node):
    """
    Return a list of string_literal nodes that are enclosed in a return_statement ancestor.
    Uses an explicit stack that carries ancestor lists to avoid relying on node.parent.
    """
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
    """
    Walk up parents (if available) to find the top-most if_statement that contains this node
    as its 'alternative' descendant. This helps us ensure we only modify each if-else chain once.
    """
    node = if_node
    # If parent pointers exist, use them; otherwise fall back to using the node itself.
    while True:
        p = getattr(node, 'parent', None)
        if not p or p.type != 'if_statement':
            break
        alt = p.child_by_field_name('alternative')
        if not alt:
            break
        # If our original if_node lies inside this parent's alternative, promote
        if if_node.start_byte >= alt.start_byte and if_node.end_byte <= alt.end_byte:
            node = p
            continue
        break
    return node


def find_else_keyword_pos(source_text, alt_start):
    """
    Try to find the 'else' keyword that begins the alternative by searching backwards
    from alt_start a modest distance. If not found, fallback to alt_start.
    """
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


def process_java_file(path, tgt_lang, src_lang="en"):
    try:
        with open(path, "rb") as f:
            source_bytes = f.read()
        
        if f'Locale.forLanguageTag("{tgt_lang}")'.encode() in source_bytes:
            return "SKIPPED_ALREADY_EXISTS"

        tree = parser.parse(source_bytes)
        root_node = tree.root_node

        # Find if_statement nodes and string literals under return statements
        if_nodes = collect_if_statements(root_node)
        str_nodes = collect_return_string_literals(root_node)

        matches = []
        for if_node in if_nodes:
            # Try to get the condition; use field name if available, fallback to searching children
            cond_node = if_node.child_by_field_name('condition')
            if not cond_node:
                # look for a parenthesized_expression child inside the if node
                stack = [if_node]
                found = None
                while stack and not found:
                    node = stack.pop()
                    if node.type == 'parenthesized_expression':
                        found = node
                        break
                    stack.extend(node.children)
                cond_node = found

            if not cond_node:
                continue

            cond_text = get_node_text(cond_node, source_bytes)

            # Logic check for Locale.ENGLISH or locale.equals(...)
            if "Locale.ENGLISH" in cond_text or "locale.equals" in cond_text.lower():
                eng_text = None
                # Find a string_literal node that lies inside this if_statement
                for s_node in str_nodes:
                    if s_node.start_byte > if_node.start_byte and s_node.end_byte < if_node.end_byte:
                        text = get_node_text(s_node, source_bytes)
                        eng_text = strip_quotes_once(text)
                        break

                if eng_text:
                    matches.append((if_node, eng_text))

        if not matches:
            return "SKIPPED_NO_MATCH"

        source_text = source_bytes.decode('utf-8')
        # We'll modify the source_text; collect unique chains so we only insert once per chain.
        processed_roots = set()
        # Process from bottom of file to top to keep byte offsets valid as we mutate
        for if_node, eng_text in reversed(matches):
            root = find_chain_root(if_node)
            key = (root.start_byte, root.end_byte)
            if key in processed_roots:
                continue
            processed_roots.add(key)

            # Walk down to the last 'if' in the else-if chain
            current = root
            while True:
                alt = current.child_by_field_name('alternative')
                if alt and alt.type == 'if_statement':
                    current = alt
                else:
                    break

            final_else = current.child_by_field_name('alternative')
            if not final_else:
                # No else branch to insert before, append an else-if after current node's end
                insert_pos = current.end_byte
            else:
                # Find position of the 'else' keyword so insertion is correct (we insert before 'else')
                insert_pos = find_else_keyword_pos(source_text, final_else.start_byte)

            # Determine indentation by looking at the line where we will insert
            lines_before = source_text[:insert_pos].splitlines()
            indent = ""
            if lines_before:
                last_line = lines_before[-1]
                indent = last_line[:len(last_line) - len(last_line.lstrip())]

            translated = translate_value(eng_text, src_lang, tgt_lang)
            # Build new else-if block; ensure it ends so the existing else/else-if remains valid
            new_code = (
                f'{indent}else if (locale.equals(Locale.forLanguageTag("{tgt_lang}"))) {{\n'
                f'{indent}    return "{translated}";\n'
                f'{indent}}}\n'
            )

            # Insert the new code
            source_text = source_text[:insert_pos] + new_code + source_text[insert_pos:]

        # Write back file only if we made changes
        with open(path, "w", encoding="utf-8") as f:
            f.write(source_text)
        return "UPDATED"

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

    print("\n" + "="*50)
    print(f"DEBUG: Scanned {found_files} .java files")
    print(f"PROCESSING SUMMARY FOR: {args.tgt_lang}")
    print("="*50)
    print(f"✅ UPDATED: {len(stats.get('UPDATED', []))}")
    print(f"⏭️  ALREADY EXISTS: {len(stats.get('SKIPPED_ALREADY_EXISTS', []))}")
    print(f"⚪ NO TARGET PATTERN: {len(stats.get('SKIPPED_NO_MATCH', []))}")
    print(f"❌ ERRORS: {len(stats.get('ERROR', []))}")
    print("="*50)


if __name__ == "__main__":
    main()