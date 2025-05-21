package io.github.jy95.fds.common.types;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * An interface for generating Markdown examples of dosage information.
 * Implementations of this interface can read dosage data from JSON files
 * and generate human-readable Markdown documentation in multiple locales.
 *
 * @param <A> The type of the DosageAPI used to convert dosage objects to human-readable text.
 * @param <B> The type of the dosage object read from the JSON files.
 */
public interface DosageMarkdown<A extends DosageAPI<?, B>, B> {

    /**
     * Returns a list of locales for which Markdown examples will be generated.
     * The default implementation provides English, French, German, and Dutch (Belgian).
     *
     * @return A list of {@link Locale} objects.
     */
    default List<Locale> getLocales() {
        return List.of(
                Locale.ENGLISH,
                Locale.FRENCH,
                Locale.GERMAN,
                Locale.forLanguageTag("nl-BE")
        );
    }

    /**
     * Returns the path to the resources directory containing the JSON input files.
     * The default implementation assumes the JSON files are located in {@code src/site/resources}.
     *
     * @return A {@link Path} object representing the resources directory.
     */
    default Path getResourcesDir() {
        return Paths.get("src", "site", "resources", "examples");
    }

    /**
     * Returns the output directory for the generated Markdown file for a specific locale and folder.
     * The default implementation creates a directory structure like
     * {@code src/site/{locale}/markdown/examples/{folderName}}.
     *
     * @param locale The {@link Locale} for which the output directory is being determined.
     * @param folder The {@link Path} to the input folder containing the JSON file.
     * @return A {@link Path} object representing the output directory.
     */
    default Path getOutputDir(Locale locale, Path folder) {
        return Paths.get("src", "site", locale.toString(), "markdown", "examples", folder.getFileName().toString());
    }

    /**
     * Extracts the base name of a JSON file (without the ".json" extension).
     *
     * @param jsonFile The {@link Path} to the JSON file.
     * @return The base name of the file as a {@link String}.
     */
    default String getBaseName(Path jsonFile) {
        return jsonFile.getFileName().toString().replace(".json", "");
    }

    /**
     * Returns an instance of the {@link DosageAPI} that will be used to convert
     * the dosage objects into human-readable text.
     *
     * @return An instance of the {@link DosageAPI}.
     */
    A getDosageAPI();

    /**
     * Reads a dosage object of type {@code B} from the specified JSON file.
     *
     * @param jsonFile The {@link Path} to the JSON file containing the dosage data.
     * @return The dosage object read from the JSON file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    List<B> getDosageFromJson(Path jsonFile) throws IOException;

    /**
     * Generates Markdown example files for all JSON files found within the subdirectories
     * of the resources directory for each of the supported locales.
     * The generated Markdown files will contain the original JSON input and the
     * human-readable output produced by the {@link DosageAPI}.
     *
     * @throws Exception If an error occurs during file processing or API usage.
     */
    default void generateMarkdown() throws Exception {
        Path resourcesDir = getResourcesDir();
        A lib = getDosageAPI();

        try (var folders = Files.list(resourcesDir).filter(Files::isDirectory)) {
            for (Path folder : folders.collect(Collectors.toList())) {
                for (Locale locale : getLocales()) {
                    Path outDir = getOutputDir(locale, folder);
                    Files.createDirectories(outDir);
                    Path mdFile = outDir.resolve(folder.getFileName() + ".md"); // Output file named after the folder

                    try (BufferedWriter writer = Files.newBufferedWriter(mdFile)) {
                        writer.write("# " + folder.getFileName() + " \n\n");
                        writer.write("<table>\n");
                        writer.write("  <thead>\n");
                        writer.write("    <tr>\n");
                        writer.write("      <th>Dosage</th>\n");
                        writer.write("      <th>Human readable text</th>\n");
                        writer.write("    </tr>\n");
                        writer.write("  </thead>\n");
                        writer.write("  <tbody>\n");

                        try (var files = Files.list(folder).filter(f -> f.toString().endsWith(".json"))) {
                            for (Path jsonFile : files.collect(Collectors.toList())) {
                                try {
                                    List<B> dosages = getDosageFromJson(jsonFile);
                                    String outputText = lib.asHumanReadableText(dosages).get();
                                    String jsonContent = escapeHtml(Files.readString(jsonFile));

                                    writer.write("    <tr>\n");
                                    writer.write("      <td><pre><code class=\"language-json\">" +
                                            jsonContent +
                                            "</code></pre></td>\n");
                                    writer.write("      <td>" + escapeHtml(outputText).replace("\n", "<br>") + "</td>\n");
                                    writer.write("    </tr>\n");
                                } catch (IOException e) {
                                    System.err.println("Error reading JSON file: " + jsonFile + " - " + e.getMessage());
                                } catch (NoSuchElementException e) {
                                    System.err.println("Error converting dosage from file: " + jsonFile + " - " + e.getMessage());
                                }
                            }
                        }

                        writer.write("  </tbody>\n");
                        writer.write("</table>\n");
                    }
                }
            }
        }
    }

    /**
     * Helper method to escape HTML special characters for safe inclusion in HTML content.
     *
     * @param text The text to escape.
     * @return The escaped text.
     */
    default String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#039;");
    }
}