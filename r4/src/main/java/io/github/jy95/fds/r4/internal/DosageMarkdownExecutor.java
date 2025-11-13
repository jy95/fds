package io.github.jy95.fds.r4.internal;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.utils.DosageMarkdownR4;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.common.config.DefaultImplementations;
import io.github.jy95.fds.common.config.FDSConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

/**
 * <p>DosageMarkdownExecutor class.</p>
 *
 * @author runner
 * @since 1.0.6
 */
public class DosageMarkdownExecutor {

    private final static String BASE_PATH = new File("").getAbsolutePath();
    private final static String ROOT_PATH = "r4";
    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * A custom implementation for the specification examples
     */
    static class SpecsR4 extends DosageMarkdownR4 {

        @Override
        public Path getResourcesDir() {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "resources", "specs");
        }

        @Override
        public Path getBaseOutputDir(Locale locale) {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "markdown", "specs");
        }

        @Override
        public List<Locale> getLocales() {
            // Only English at the moment
            return List.of(Locale.ENGLISH);
        }

        @Override
        public DosageAPIR4 createDosageAPI(Locale locale) {

            // Add the text in the rendering order as by default, it isn't
            var renderOrder = Stream
                    .concat(
                            FDSConfig
                                    .builder()
                                    .build()
                                    .getDisplayOrder()
                                    .stream(),
                            Stream.of(DisplayOrder.TEXT)
                    )
                    .toList();

            // Return custom instance for docs
            return new DosageAPIR4(
                    FDSConfigR4
                            .builder()
                            .displayOrder(renderOrder)
                            .locale(locale)
                            .build()
            );
        }
    }

    /**
     * A custom implementation for the timing examples
     */
    static class TimingR4 extends SpecsR4 {
        @Override
        public Path getResourcesDir() {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "resources", "timing");
        }

        @Override
        public Path getBaseOutputDir(Locale locale) {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "markdown", "timing");
        }
    }

    /**
     * A custom implementation for the DosageAPIR4, to support fallback to the dosage text
     * when the human-readable text is empty.
     */
    static class DosageAPIR4Custom extends DosageAPIR4 {

        /**
         * <p>Constructor for DosageAPIR4Custom.</p>
         *
         * @param config a {@link io.github.jy95.fds.r4.config.FDSConfigR4} object
         */
        public DosageAPIR4Custom(FDSConfigR4 config) {
            super(config);
        }

        @Override
        public CompletableFuture<String> asHumanReadableText(Dosage dosage) {
            // Use the default implementation for now
            // but ensure that if the result is empty, we return the text from the dosage
            var result = super.asHumanReadableText(dosage);
            return result.thenApply(text -> text.isEmpty() ? dosage.getText() : text);
        }
    }

    /**
     * A custom implementation for the medicationrequest examples
     */
    static class MedicationRequestR4 extends SpecsR4 {

        /**
         * The FHIR context for R4.
         */
        private static final IParser JSON_PARSER = FhirContext.forR4().newJsonParser();

        @Override
        public Path getResourcesDir() {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "resources", "medicationrequest");
        }

        @Override
        public Path getBaseOutputDir(Locale locale) {
            return Paths.get(BASE_PATH,ROOT_PATH, "src", "site", "markdown", "medicationrequest");
        }

        @Override
        public DosageAPIR4 createDosageAPI(Locale locale) {
            return new DosageAPIR4Custom(
                    FDSConfigR4
                            .builder()
                            .displayOrder(DefaultImplementations.DEFAULT_DISPLAY_ORDER)
                            .locale(locale)
                            .build()
            );
        }

        @Override
        public List<Dosage> getDosageFromJson(Path jsonFile) throws IOException {
            String finalJson = Files.readString(jsonFile).trim();
            MedicationRequest mr = JSON_PARSER.parseResource(MedicationRequest.class, finalJson);
            
            return mr.getDosageInstruction();
        }

        @Override
        public String getDosageJsonAsString(Path jsonFile) throws IOException {
            String finalJson = super.getDosageJsonAsString(jsonFile);

            var rootNode = MAPPER.readTree(finalJson);
            var dosageNode = rootNode.get("dosageInstruction");

            return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(dosageNode);
        }
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        var specsExamples = new SpecsR4();
        specsExamples.generateMarkdown();
        var timingExamples = new TimingR4();
        timingExamples.generateMarkdown();
        var medicationRequestExamples = new MedicationRequestR4();
        medicationRequestExamples.generateMarkdown();
    }
}
