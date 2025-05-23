package io.github.jy95.fds.r4.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.MedicationKnowledge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import io.github.jy95.fds.common.types.DosageMarkdown;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;

/**
 * An interface for generating Markdown examples of dosage information.
 * Implementations of this interface can read dosage R4 data from JSON files
 * and generate human-readable Markdown documentation in multiple locales.
 *
 * @author jy95
 * @since 1.0.5
 */
public class DosageMarkdownR4 implements DosageMarkdown<DosageAPIR4, Dosage> {

    /**
     * The FHIR context for R4.
     */
    private static final IParser JSON_PARSER = FhirContext.forR4().newJsonParser();

    /**
     * The ObjectMapper for JSON parsing.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * The base JSON template for the MedicationKnowledge resource.
     */
    private static final ObjectNode BASE_TEMPLATE = createJsonTemplate();


    /**
     * The JSON parser for parsing dosage data.
     */
    private static ObjectNode createJsonTemplate() {
        ObjectNode root = MAPPER.createObjectNode();
        root.put("resourceType", "MedicationKnowledge");
        root.put("status", "active");

        ObjectNode code = MAPPER.createObjectNode();
        code.put("text", "dummy");
        root.set("code", code);

        // Create an empty dosageGuideline array with one object ready for dosage injection
        ObjectNode guideline = MAPPER.createObjectNode();
        guideline.set("dosage", MAPPER.createArrayNode());

        ArrayNode guidelineArray = MAPPER.createArrayNode();
        guidelineArray.add(guideline);
        root.set("dosageGuideline", guidelineArray);

        return root;
    }

    /** {@inheritDoc} */
    @Override
    public List<Dosage> getDosageFromJson(Path jsonFile) throws IOException {
        String dosageJson = Files.readString(jsonFile).trim();
        JsonNode dosageNode = MAPPER.readTree(dosageJson);

        // Ensure it's an array
        ArrayNode dosageArray = dosageNode.isArray()
            ? (ArrayNode) dosageNode
            : MAPPER.createArrayNode().add(dosageNode);

        // Deep copy the base template
        ObjectNode workingCopy = BASE_TEMPLATE.deepCopy();

        // Inject into guideline[0].dosage
        ArrayNode guidelineArray = (ArrayNode) workingCopy.get("dosageGuideline");
        ObjectNode firstGuideline = (ObjectNode) guidelineArray.get(0);
        firstGuideline.set("dosage", dosageArray);

        // Convert back to MedicationKnowledge
        String finalJson = MAPPER.writeValueAsString(workingCopy);
        MedicationKnowledge mk = JSON_PARSER.parseResource(MedicationKnowledge.class, finalJson);

        // Return the dosage from the first guideline
        return mk.getAdministrationGuidelinesFirstRep().getDosageFirstRep().getDosage();
    }

    /** {@inheritDoc} */
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
