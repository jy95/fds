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
     * Returns the base output directory for generated Markdown files for a specific locale.
     * All Markdown files will be placed under this directory, potentially in subdirectories
     * that mirror the input folder structure.
     *
     * @param locale The {@link Locale} for which the base output directory is being determined.
     * @return A {@link Path} object representing the base output directory (e.g., src/site/en/markdown/examples).
     */
    default Path getBaseOutputDir(Locale locale) {
        return Paths.get("src", "site", locale.toString(), "markdown", "examples");
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
     * @throws Exception If an error occurs during file processing or API usage.
     */
    default void generateMarkdown() throws Exception {
        Map<Locale, A> dosageAPIs = getDosageAPIs();
        Map<Path, List<Path>> groupedJsonFiles = getJsonFilesGroupedByFolder();

        for (Locale locale : getLocales()) {
            
            // Prepare the main folder for the locale
            Path baseOutputDir = getBaseOutputDir(locale);
            Files.createDirectories(baseOutputDir);

            // Prepare the dosage API for the current locale
            A dosageApiForLocale = dosageAPIs.get(locale);

            // Iterate through the grouped JSON files
            for (Map.Entry<Path, List<Path>> entry : groupedJsonFiles.entrySet()) {

                Path folder = entry.getKey();
                List<Path> jsonFilesInFolder = entry.getValue();

                // Fill in the markdown file with the JSON files
                Path markdownFile = baseOutputDir.resolve(getRelativeMarkdownFilePath(folder));
                Files.createDirectories(markdownFile.getParent()); // Ensure parent directories for the markdown file exist
                generateMarkdownForFolderAndLocale(dosageApiForLocale, folder, locale, markdownFile, jsonFilesInFolder);
            }
        }
    }

    /**
     * Generates the Markdown content for a specific folder and locale.
     *
     * @param dosageApi          The {@link DosageAPI} instance configured for the specific locale.
     * @param inputFolder        The {@link Path} to the input folder containing JSON files.
     * @param locale             The {@link Locale} for which to generate the Markdown.
     * @param markdownFile       The {@link Path} to the output Markdown file.
     * @param jsonFilesToProcess The {@link List} of {@link Path} objects representing JSON files to be processed for this folder.
     * @throws IOException          If an I/O error occurs while reading or writing files.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void generateMarkdownForFolderAndLocale(A dosageApi, Path inputFolder, Locale locale, Path markdownFile, List<Path> jsonFilesToProcess) throws IOException, InterruptedException, ExecutionException {
        try (BufferedWriter writer = Files.newBufferedWriter(markdownFile)) {
            // The header should be the name of the folder (e.g., "examples", "text", "nested")
            String headerName = inputFolder.getFileName().toString();
            writeMarkdownHeader(writer, headerName);
            writeMarkdownTableStart(writer);

            for (Path jsonFile : jsonFilesToProcess) {
                processJsonFile(dosageApi, jsonFile, locale, writer);
            }

            writeMarkdownTableEnd(writer);
        }
    }

    /**
     * Determines the relative path and filename for the markdown output file.
     * This method constructs the path relative to the base output directory
     * (e.g., "examples.md", "text/text.md", "text/nested/nested.md").
     *
     * @param folder The {@code Path} of the source folder (e.g., src/site/resources/examples/text).
     * @return A {@code String} representing the relative path and filename from the base output directory.
     */
    private String getRelativeMarkdownFilePath(Path folder) {

        // Get the resources directory and the file name for the markdown output
        Path resourcesDir = getResourcesDir();
        String fileName = folder.getFileName().toString() + ".md";

        // Get the relative path from the resources directory to the folder
        // e.g., "", "text", "text/nested"
        Path relativePathFromResources = resourcesDir.relativize(folder);
        var nameCount = relativePathFromResources.getNameCount();
        
        if (nameCount <= 1) {
            // If the folder is the resources directory itself or empty, return the file name only.
            return fileName;
        } else {
            // For subdirectories, we need to combine the relative path - 1 (the folder name) with the filename.
            return relativePathFromResources
                .subpath(0, nameCount - 1)
                .resolve(fileName)
                .toString();
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
     * @throws IOException          If an I/O error occurs while reading or writing files.
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private void processJsonFile(A dosageApi, Path jsonFile, Locale locale, BufferedWriter writer) throws IOException, InterruptedException, ExecutionException {
        try {
            List<B> dosages = getDosageFromJson(jsonFile);
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
        writer.write("    </tr>\n");
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
