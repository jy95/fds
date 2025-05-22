package io.github.jy95.fds.common.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * The default implementation assumes the JSON files are located in {@code src/site/resources/examples}.
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
        String outputFolderName = getOutputName(folder);
        return Paths.get("src", "site", locale.toString(), "markdown", "examples", outputFolderName);
    }

    /**
     * Determines the name of the output folder based on the provided folder path
     * relative to the resources directory.
     *
     * @param folder The {@code Path} of the folder for which to determine the output name.
     * @return A {@code String} representing the name of the output folder.
     * If the provided {@code folder} is the same as the resources directory,
     * the output folder name will be "general". Otherwise, the output
     * folder name will be the string representation of the path of the
     * {@code folder} relative to the resources directory.
     */
    default String getOutputName(Path folder) {
        Path resourcesDir = getResourcesDir();
        Path relativePath = resourcesDir.relativize(folder); // e.g., "conditions" or "" (empty path for root)

        String outputFolderName;
        if (relativePath.toString().isEmpty()) {
            // If the folder is the resourcesDir itself, use "general" as the output folder name
            outputFolderName = "general";
        } else {
            // Otherwise, use the relative path's string as the output folder name
            outputFolderName = relativePath.toString();
        }

        return outputFolderName;
    }

    /**
     * Creates and returns a new instance of {@link DosageAPI} for the given locale.
     * Implementers of this interface must provide a concrete implementation for this method.
     *
     * @param locale The {@link Locale} for which to create the {@link DosageAPI}.
     * @return A new {@link DosageAPI} instance configured for the specified locale.
     */
    A createDosageAPI(Locale locale);

    /**
     * Returns a map of {@link DosageAPI} instances, keyed by {@link Locale}.
     * This default implementation uses {@link #getLocales()} and {@link #createDosageAPI(Locale)}
     * to build the map.
     *
     * @return A {@link Map} where keys are {@link Locale} objects and values are
     * {@link DosageAPI} instances configured for that locale.
     */
    default Map<Locale, A> getDosageAPIs() {
        return getLocales()
            .stream()
            .collect(Collectors.toMap(
                locale -> locale,
                this::createDosageAPI // Uses the abstract createDosageAPI method
            ));
    }

    /**
     * Reads a dosage object of type {@code B} from the specified JSON file.
     *
     * @param jsonFile The {@link Path} to the JSON file containing the dosage data.
     * @return The dosage object read from the JSON file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    List<B> getDosageFromJson(Path jsonFile) throws IOException;

    /**
     * Returns a Stream of {@link Path} objects representing all JSON files
     * recursively found within the resources directory and its subdirectories.
     * This method can be overridden to customize how the JSON files are located.
     *
     * @return A {@link Stream} of {@link Path} objects for the JSON files.
     * @throws IOException If an I/O error occurs while traversing directories.
     */
    default Stream<Path> getJsonFiles() throws IOException {
        return Files
                .walk(getResourcesDir())
                .filter(path -> path.toString().endsWith(".json"));
    }

    /**
     * Returns a map of JSON files grouped by their parent directory.
     * This method uses {@link #getJsonFiles()} to find all JSON files
     * and then groups them by their containing folder.
     *
     * @return A {@link Map} where keys are {@link Path} objects representing folders,
     * and values are {@link List} of {@link Path} objects representing JSON files
     * within that folder.
     * @throws IOException If an I/O error occurs while traversing directories.
     */
    default Map<Path, List<Path>> getJsonFilesGroupedByFolder() throws IOException {
        try (Stream<Path> jsonFilesStream = getJsonFiles()) {
            return jsonFilesStream
                    .collect(Collectors.groupingBy(Path::getParent));
        }
    }

    /**
     * Generates Markdown example files for all JSON files found within the subdirectories
     * of the resources directory for each of the supported locales.
     * The generated Markdown files will contain the original JSON input and the
     * human-readable output produced by the {@link DosageAPI}.
     *
     * This method now uses {@link #getJsonFilesGroupedByFolder()} to iterate through
     * folders and their associated JSON files.
     *
     * @throws Exception If an error occurs during file processing or API usage.
     */
    default void generateMarkdown() throws Exception {
        Map<Locale, A> dosageAPIs = getDosageAPIs(); // Get the map of APIs
        Map<Path, List<Path>> groupedJsonFiles = getJsonFilesGroupedByFolder(); // Get files grouped by folder

        for (Map.Entry<Path, List<Path>> entry : groupedJsonFiles.entrySet()) {
            Path folder = entry.getKey();
            List<Path> jsonFilesInFolder = entry.getValue();

            // Determine the output folder name for the markdown file
            String outputFolderName = folder.getFileName() != null ? folder.getFileName().toString() : "general"; // For root level files

            for (Locale locale : getLocales()) {
                Path outputDir = getOutputDir(locale, folder); // Use the original folder path to determine output directory
                Files.createDirectories(outputDir);
                Path markdownFile = outputDir.resolve(outputFolderName + ".md");

                // Get the specific DosageAPI for the current locale from the map
                A dosageApiForLocale = dosageAPIs.get(locale);

                // Generate Markdown for the current folder and locale using the grouped files
                generateMarkdownForFolderAndLocale(dosageApiForLocale, folder, locale, markdownFile, jsonFilesInFolder);
            }
        }
    }

    /**
     * Generates the Markdown content for a specific folder and locale.
     * This overloaded method now accepts a list of JSON files to process for the given folder.
     *
     * @param dosageApi     The {@link DosageAPI} instance configured for the specific locale.
     * @param inputFolder   The {@link Path} to the input folder containing JSON files.
     * @param locale        The {@link Locale} for which to generate the Markdown.
     * @param markdownFile The {@link Path} to the output Markdown file.
     * @param jsonFilesToProcess The {@link List} of {@link Path} objects representing JSON files to be processed for this folder.
     * @throws IOException If an I/O error occurs while reading or writing files.
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    private void generateMarkdownForFolderAndLocale(A dosageApi, Path inputFolder, Locale locale, Path markdownFile, List<Path> jsonFilesToProcess) throws IOException, InterruptedException, ExecutionException {
        try (BufferedWriter writer = Files.newBufferedWriter(markdownFile)) {
            writeMarkdownHeader(writer, inputFolder.getFileName().toString());
            writeMarkdownTableStart(writer);

            // Iterate directly over the provided list of JSON files
            for (Path jsonFile : jsonFilesToProcess) {
                processJsonFile(dosageApi, jsonFile, locale, writer);
            }

            writeMarkdownTableEnd(writer);
        }
    }

    /**
     * Writes the Markdown header to the output file.
     *
     * @param writer     The {@link BufferedWriter}.
     * @param folderName The name of the folder.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private void writeMarkdownHeader(BufferedWriter writer, String folderName) throws IOException {
        writer.write("# " + folderName + " \n\n");
    }

    /**
     * Writes the start of the Markdown table.
     *
     * @param writer The {@link BufferedWriter}.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private void writeMarkdownTableStart(BufferedWriter writer) throws IOException {
        writer.write("<table>\n");
        writer.write("  <thead>\n");
        writer.write("    <tr>\n");
        writer.write("      <th>Dosage</th>\n");
        writer.write("      <th>Human readable text</th>\n");
        writer.write("    </tr>\n");
        writer.write("  </thead>\n");
        writer.write("  <tbody>\n");
    }

    /**
     * Processes a single JSON file, reads the dosage data, generates the human-readable text,
     * and writes a row to the Markdown table.
     *
     * @param dosageApi The {@link DosageAPI} instance configured for the specific locale.
     * @param jsonFile  The {@link Path} to the JSON file.
     * @param locale    The {@link Locale} for which to generate the text (used for error logging if needed).
     * @param writer    The {@link BufferedWriter}.
     * @throws IOException If an I/O error occurs while reading or writing files.
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    private void processJsonFile(A dosageApi, Path jsonFile, Locale locale, BufferedWriter writer) throws IOException, InterruptedException, ExecutionException {
        try {
            List<B> dosages = getDosageFromJson(jsonFile);
            // The dosageApi is already configured for the correct locale, no need to set it here.
            String outputText = dosageApi.asHumanReadableText(dosages).get();
            String jsonContent = escapeHtml(Files.readString(jsonFile));
            writeMarkdownTableRow(writer, jsonContent, outputText);
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + jsonFile + " - " + e.getMessage());
        }
    }

    /**
     * Writes a single row to the Markdown table.
     *
     * @param writer      The {@link BufferedWriter}.
     * @param jsonContent The escaped JSON content.
     * @param outputText  The escaped human-readable text.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private void writeMarkdownTableRow(BufferedWriter writer, String jsonContent, String outputText) throws IOException {
        writer.write("    <tr>\n");
        writer.write("      <td><pre><code class=\"language-json\">" + jsonContent + "</code></pre></td>\n");
        writer.write("      <td>" + escapeHtml(outputText).replace("\n", "<br>") + "</td>\n");
    }

    /**
     * Writes the end of the Markdown table.
     *
     * @param writer The {@link BufferedWriter}.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private void writeMarkdownTableEnd(BufferedWriter writer) throws IOException {
        writer.write("  </tbody>\n");
        writer.write("</table>\n");
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
