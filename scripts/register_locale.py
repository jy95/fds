#!/usr/bin/env python3
"""
Register a new locale (BCP 47) across the repository.

What it updates:
- publiccode.yml: adds the BCP 47 code to localisation.availableLanguages
- common/src/main/java/.../DosageMarkdown.java: adds Locale.forLanguageTag("<tgt>") to getLocales list
- common/src/test/java/.../LocaleProviderBase.java: adds Locale.forLanguageTag("<tgt>") to localeProvider Stream
- r4/src/test/java/.../AbstractFhirTest.java: adds Locale.forLanguageTag("<tgt>") to localeProvider Stream
- r5/src/test/java/.../AbstractFhirTest.java: adds Locale.forLanguageTag("<tgt>") to localeProvider Stream

Usage:
    python scripts/register_locale.py --tgt-lang pt-BR        # preview changes
    python scripts/register_locale.py --tgt-lang pt-BR --apply  # apply changes

This script attempts to use tree-sitter to find method bodies and locate the Stream.of(...) or List.of(...) call,
then inserts a Locale.forLanguageTag(...) entry if not already present.

Note: The script does NOT commit to Git; it writes files in-place when --apply is supplied.
"""
import argparse
import io
import os
import re
import sys
from typing import Optional, Tuple

try:
    from tree_sitter import Language, Parser
    import tree_sitter_java as tsjava
except Exception:
    print("ERROR: tree_sitter and tree_sitter_java are required. Install them first.")
    print("See: https://github.com/tree-sitter/py-tree-sitter and the tree-sitter-java binding.")
    sys.exit(1)

try:
    import yaml
except Exception:
    yaml = None  # we'll do a fallback edit

JAVA_LANGUAGE = Language(tsjava.language())
parser = Parser()
parser.set_language(JAVA_LANGUAGE)


def get_node_text(node, src_bytes: bytes) -> str:
    return src_bytes[node.start_byte:node.end_byte].decode("utf-8")


def find_method_by_name(root, name: str):
    # Find method_declaration whose name identifier equals `name`
    stack = [root]
    while stack:
        node = stack.pop()
        if node.type == "method_declaration":
            name_node = node.child_by_field_name("name")
            if name_node and get_node_text(name_node, src_bytes).strip() == name:
                return node
        stack.extend(node.children)
    return None


def find_all_method_declarations(root):
    stack = [root]
    out = []
    while stack:
        node = stack.pop()
        if node.type == "method_declaration":
            out.append(node)
        stack.extend(node.children)
    return out


def insert_into_of_call(method_text: str, tgt_entry: str, indentation: str) -> Tuple[str, bool]:
    """
    Find the last occurrence of Stream.of( or List.of( in method_text and insert tgt_entry
    before the closing ')' of that call. Returns (new_text, changed).
    """
    # Try to find the .of( invocation - prefer Stream.of then List.of then Arrays.asList style if needed
    idx = None
    for pattern in [r"\bStream\s*\.\s*of\s*\(", r"\bList\s*\.\s*of\s*\("]:
        m = list(re.finditer(pattern, method_text))
        if m:
            idx = m[-1].start()
            break
    if idx is None:
        # fallback: look for ".of(" tokens
        m = list(re.finditer(r"\.of\s*\(", method_text))
        if m:
            idx = m[-1].start()

    if idx is None:
        return method_text, False

    # From index of '(...' find the matching closing parenthesis
    start_paren = method_text.find("(", idx)
    if start_paren == -1:
        return method_text, False

    depth = 0
    pos = start_paren
    end_pos = -1
    while pos < len(method_text):
        ch = method_text[pos]
        if ch == "(":
            depth += 1
        elif ch == ")":
            depth -= 1
            if depth == 0:
                end_pos = pos
                break
        pos += 1

    if end_pos == -1:
        return method_text, False

    # Extract the content inside the of(...) list
    inside = method_text[start_paren + 1 : end_pos]
    if tgt_entry in inside:
        # already present
        return method_text, False

    # Clean trailing whitespace/newlines before ')'
    # Decide how to insert: add a comma+newline+indent if inside has newlines else add ", " + entry
    if "\n" in inside:
        # Determine base indentation for new element
        # Find the line where the of( starts
        line_start = method_text.rfind("\n", 0, start_paren) + 1
        base_indent = method_text[line_start : start_paren]
        # Determine nested indent used in the list (look at first non-empty line inside)
        m = re.search(r"\n(\s*)\S", inside)
        nested_indent = m.group(1) if m else base_indent + "    "
        insert_piece = f",\n{nested_indent}{tgt_entry}"
        new_inside = inside.rstrip() + insert_piece
    else:
        # single-line call like List.of(Locale.ENGLISH, Locale.FRENCH)
        # insert as ', ' + entry
        new_inside = inside.strip()
        if new_inside == "":
            new_inside = tgt_entry
        else:
            new_inside = new_inside + ", " + tgt_entry

    new_method_text = method_text[: start_paren + 1] + new_inside + method_text[end_pos:]
    return new_method_text, True


def update_java_file(path: str, method_names: list, tgt_lang: str, apply: bool = False) -> Tuple[bool, Optional[str]]:
    """
    Update the Java file at path by inserting Locale.forLanguageTag("<tgt_lang>") into any of the methods in method_names.
    Returns (changed, preview_text or None)
    """
    with open(path, "rb") as f:
        src = f.read()
    src_text = src.decode("utf-8")
    tree = parser.parse(src)

    root = tree.root_node

    changed_any = False
    new_src_text = src_text

    # find method_declaration nodes and inspect name
    for method_node in find_all_method_declarations(root):
        name_node = method_node.child_by_field_name("name")
        if not name_node:
            continue
        method_name = get_node_text(name_node, src)
        if method_name not in method_names:
            continue
        # extract method text
        method_text = get_node_text(method_node, src)
        # Construct insertion entry
        tgt_entry = f'Locale.forLanguageTag("{tgt_lang}")'
        new_method_text, changed = insert_into_of_call(method_text, tgt_entry, indentation="")
        if changed:
            changed_any = True
            # replace the first occurrence of method_text in new_src_text with new_method_text
            # (we use indices to be safer)
            start = method_node.start_byte
            end = method_node.end_byte
            new_src_text = new_src_text[:start] + new_method_text + new_src_text[end:]
            # After modification, reparse the new source so subsequent indices align
            src = new_src_text.encode("utf-8")
            tree = parser.parse(src)
            root = tree.root_node

    if changed_any:
        if apply:
            with open(path, "w", encoding="utf-8") as f:
                f.write(new_src_text)
            return True, None
        else:
            return True, new_src_text
    else:
        return False, None


def update_publiccode_yaml(path: str, tgt_lang: str, apply: bool = False) -> Tuple[bool, Optional[str]]:
    """
    Add the string tgt_lang to localisation.availableLanguages in publiccode.yml
    If PyYAML is available, use it; otherwise operate with a simple textual insertion.
    """
    with open(path, "r", encoding="utf-8") as f:
        text = f.read()

    # Try to load YAML if possible
    if yaml:
        doc = yaml.safe_load(text)
        loc = doc.get("localisation", {})
        avail = loc.get("availableLanguages", [])
        if tgt_lang in avail:
            return False, None
        avail.append(tgt_lang)
        # keep ordering and format simple
        doc.setdefault("localisation", {})["availableLanguages"] = avail
        new_text = yaml.safe_dump(doc, sort_keys=False, allow_unicode=True)
        if apply:
            with open(path, "w", encoding="utf-8") as f:
                f.write(new_text)
            return True, None
        return True, new_text
    else:
        # Fallback: look for "availableLanguages:" block and insert item if missing
        block_re = re.compile(r"(localisation:\s*\n(?:\s+\S.*\n)*?)availableLanguages:\s*\n", re.MULTILINE)
        m = re.search(r"availableLanguages:\s*\n((?:\s*-\s*.*\n)+)", text)
        if not m:
            # cannot find block; append near end
            insert_point = len(text)
            piece = f"\nlocalisation:\n  localisationReady: true\n  availableLanguages:\n    - {tgt_lang}\n"
            new_text = text + piece
            if apply:
                with open(path, "w", encoding="utf-8") as f:
                    f.write(new_text)
                return True, None
            return True, new_text
        items_block = m.group(1)
        if re.search(rf"-\s*{re.escape(tgt_lang)}\b", items_block):
            return False, None
        # insert new line in the items block (we replace first occurrence)
        new_items_block = items_block + f"    - {tgt_lang}\n"
        new_text = text[: m.start(1)] + new_items_block + text[m.end(1) :]
        if apply:
            with open(path, "w", encoding="utf-8") as f:
                f.write(new_text)
            return True, None
        return True, new_text


def preview_or_apply(tgt_lang: str, apply: bool):
    # Files to edit
    repo_root = "."
    publiccode = os.path.join(repo_root, "publiccode.yml")
    dosage_markdown = os.path.join(repo_root,
                                  "common", "src", "main", "java",
                                  "io", "github", "jy95", "fds", "common", "types", "DosageMarkdown.java")
    locale_provider = os.path.join(repo_root,
                                   "common", "src", "test", "java",
                                   "io", "github", "jy95", "fds", "utilities", "LocaleProviderBase.java")
    r4_test = os.path.join(repo_root,
                           "r4", "src", "test", "java",
                           "io", "github", "jy95", "fds", "r4", "AbstractFhirTest.java")
    r5_test = os.path.join(repo_root,
                           "r5", "src", "test", "java",
                           "io", "github", "jy95", "fds", "r5", "AbstractFhirTest.java")

    java_method_names_by_file = {
        dosage_markdown: ["getLocales"],
        locale_provider: ["localeProvider"],
        r4_test: ["localeProvider"],
        r5_test: ["localeProvider"],
    }

    print("Previewing updates for target language:", tgt_lang)
    print("=" * 70)

    # Update publiccode.yml
    ok, preview = update_publiccode_yaml(publiccode, tgt_lang, apply=apply)
    if ok:
        if apply:
            print(f"WROTE: {publiccode}")
        else:
            print(f"Would update: {publiccode}")
            print("--- preview ---")
            print(preview)
            print("---------------")
    else:
        print(f"No changes needed for {publiccode}")

    # Update Java files
    for path, method_names in java_method_names_by_file.items():
        if not os.path.exists(path):
            print(f"SKIP (not found): {path}")
            continue
        changed, preview = update_java_file(path, method_names, tgt_lang, apply=apply)
        if changed:
            if apply:
                print(f"WROTE: {path}")
            else:
                print(f"Would update: {path}")
                print("--- preview (first 300 chars) ---")
                print(preview[:300])
                print("... (snipped) ...")
        else:
            print(f"No changes needed for {path}")


if __name__ == "__main__":
    argp = argparse.ArgumentParser()
    argp.add_argument("--tgt-lang", required=True, help="BCP47 code to register (e.g. pt-BR)")
    argp.add_argument("--apply", action="store_true", help="Write changes to files instead of preview")
    args = argp.parse_args()

    tgt = args.tgt_lang.strip()
    if not tgt:
        print("ERROR: --tgt-lang cannot be empty")
        sys.exit(2)

    preview_or_apply(tgt, apply=args.apply)
