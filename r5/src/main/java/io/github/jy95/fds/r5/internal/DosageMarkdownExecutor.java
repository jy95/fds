package io.github.jy95.fds.r5.internal;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.utils.DosageMarkdownR5;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.common.config.FDSConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.MedicationRequest;

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
    private final static String ROOT_PATH = "r5";

    /**
     * A custom implementation for the specification examples
     */
    static class SpecsR5 extends DosageMarkdownR5 {

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
        public DosageAPIR5 createDosageAPI(Locale locale) {

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
                    .collect(Collectors.toList());

            // Return custom instance for docs
            return new DosageAPIR5(
                    FDSConfigR5
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
    static class TimingR5 extends SpecsR5 {
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
    static class DosageAPIR5Custom extends DosageAPIR5 {

        /**
         * <p>Constructor for DosageAPIR5Custom.</p>
         *
         * @param config a {@link io.github.jy95.fds.r5.config.FDSConfigR5} object
         */
        public DosageAPIR5Custom(FDSConfigR5 config) {
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
    static class MedicationRequestR5 extends SpecsR5 {

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
        public DosageAPIR5 createDosageAPI(Locale locale) {
            return new DosageAPIR5Custom(
                    FDSConfigR5
                            .builder()
                            .displayOrder(FDSConfig.builder().build().getDisplayOrder())
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
    }

    /**
     * <p>main.</p>
     *
     * @param args an array of {@link java.lang.String} objects
     * @throws java.lang.Exception if any.
     */
    public static void main(String[] args) throws Exception {
        var specsExamples = new SpecsR5();
        specsExamples.generateMarkdown();
        var timingExamples = new TimingR5();
        timingExamples.generateMarkdown();
        var medicationRequestExamples = new MedicationRequestR5();
        medicationRequestExamples.generateMarkdown();
    }
}
