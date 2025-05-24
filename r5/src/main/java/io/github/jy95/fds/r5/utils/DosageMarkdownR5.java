package io.github.jy95.fds.r5.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.MedicationKnowledge;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import io.github.jy95.fds.common.types.DosageMarkdown;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;

/**
 * An interface for generating Markdown examples of dosage information.
 * Implementations of this interface can read dosage R5 data from JSON files
 * and generate human-readable Markdown documentation in multiple locales.
 *
 * @author jy95
 * @since 1.0.5
 */
public class DosageMarkdownR5 implements DosageMarkdown<DosageAPIR5, Dosage> {

    /**
     * The FHIR context for R5.
     */
    private static final IParser JSON_PARSER = FhirContext.forR5().newJsonParser();

    /**
     * The ObjectMapper for JSON parsing.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * The base JSON template for the MedicationKnowledge resource.
     */
    private static final ObjectNode BASE_TEMPLATE = createJsonTemplate();

    
    @Override
    public DosageAPIR5 createDosageAPI(Locale locale) {
        return new DosageAPIR5(
            FDSConfigR5
                .builder()
                .locale(locale)
                .build()
        );
    }

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

        // Inject dosage JSON into the correct location of the static template
        ArrayNode dosageList = (ArrayNode)
            workingCopy
                .withObject("indicationGuideline")
                .withArray("dosingGuideline")
                .get(0)
                .withArray("dosage")
                .get(0)
                .withArray("dosage");

        for (JsonNode dosage : dosageArray) {
            dosageList.add(dosage);
        }
        
        // Convert back to MedicationKnowledge
        String finalJson = MAPPER.writeValueAsString(workingCopy);
        MedicationKnowledge mk = JSON_PARSER.parseResource(MedicationKnowledge.class, finalJson);

        // Return the dosage from the first guideline
        return mk.getIndicationGuidelineFirstRep().getDosingGuidelineFirstRep().getDosageFirstRep().getDosage();
    }

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

        // Create an empty dosingGuideline array with one object ready for dosage injection
        ObjectNode dosingGuideline = MAPPER.createObjectNode();

        // Add the type attribute
        ObjectNode type = MAPPER.createObjectNode();
        ArrayNode codingArray = MAPPER.createArrayNode();
        ObjectNode coding = MAPPER.createObjectNode();
        coding.put("system", "http://terminology.hl7.org/CodeSystem/dose-rate-type");
        coding.put("code", "ordered");
        codingArray.add(coding);
        type.set("coding", codingArray);
        dosingGuideline.set("type", type);

        ArrayNode dosageArray = MAPPER.createArrayNode();
        ObjectNode dosageInstruction = MAPPER.createObjectNode();
        dosageInstruction.set("dosage", MAPPER.createArrayNode());
        dosageArray.add(dosageInstruction);
        dosingGuideline.set("dosage", dosageArray);

        ArrayNode dosingGuidelineArray = MAPPER.createArrayNode();
        dosingGuidelineArray.add(dosingGuideline);
        root.set("indicationGuideline", MAPPER.createArrayNode());
        ObjectNode indicationGuideline = MAPPER.createObjectNode();
        indicationGuideline.set("dosingGuideline", dosingGuidelineArray);
        root.set("indicationGuideline", indicationGuideline);

        return root;
    }
    
}
