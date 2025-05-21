package io.github.jy95.fds.r4.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationKnowledge;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import io.github.jy95.fds.common.types.DosageMarkdown;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;

/**
 * An interface for generating Markdown examples of dosage information.
 * Implementations of this interface can read dosage R4 data from JSON files
 * and generate human-readable Markdown documentation in multiple locales.
 */
public class DosageMarkdownR4 implements DosageMarkdown<DosageAPIR4, Dosage> {

    /**
     * The FHIR context for R4.
     */
    IParser jsonParser = FhirContext.forR4().newJsonParser();

    // Template: %s will be replaced by either a single object or an array of objects
    private static final String TEMPLATE =
        "{\n" +
        "  \"resourceType\": \"MedicationKnowledge\",\n" +
        "  \"status\": \"active\",\n" +
        "  \"code\": { \"text\": \"dummy\" },\n" +
        "  \"dosageGuideline\": [\n" +
        "    {\n" +
        "      \"dosage\": %s\n" +
        "    }\n" +
        "  ]\n" +
        "}";

    @Override
    public List<Dosage> getDosageFromJson(Path jsonFile) throws IOException {
        String dosageJson = Files.readString(jsonFile);
        String cleanDosageJson = dosageJson.trim();
        String dosagePart = cleanDosageJson.startsWith("[") ? cleanDosageJson : "[" + cleanDosageJson + "]";
        String wrappedJson = String.format(TEMPLATE, dosagePart);
        MedicationKnowledge mk = jsonParser.parseResource(MedicationKnowledge.class, wrappedJson);
        return mk.getAdministrationGuidelinesFirstRep().getDosageFirstRep().getDosage();
    }

    @Override
    public DosageAPIR4 createDosageAPI(Locale locale) {
        return new DosageAPIR4(
            FDSConfigR4
                .builder()
                .locale(locale)
                .build()
        );
    }
    
}
