package jy95.fhir.r4.dosage.utils.config;

import lombok.Builder;
import lombok.Getter;

import java.util.Locale;
import java.util.List;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.CodeableConcept;

// To provide a configuration with the
@Getter
@Builder(toBuilder = true)
public class FDUConfig {
    /*
    * Locale to return the humanized dosage string
    * Default : English
    * */
    @Builder.Default private Locale locale = Locale.getDefault();
    /**
     * Control the display order used by the algorithm
     * Useful when you want to turn on / off some specific rules for some reason
     */
    @Builder.Default private List<DisplayOrder> displayOrder = List.of(
            DisplayOrder.METHOD,
            DisplayOrder.DOSE_QUANTITY,
            DisplayOrder.DOSE_RANGE,
            DisplayOrder.RATE_RATIO,
            DisplayOrder.RATE_QUANTITY,
            DisplayOrder.RATE_RANGE,
            DisplayOrder.DURATION_DURATION_MAX,
            DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
            DisplayOrder.OFFSET_WHEN,
            DisplayOrder.DAY_OF_WEEK,
            DisplayOrder.TIME_OF_DAY,
            DisplayOrder.ROUTE,
            DisplayOrder.SITE,
            DisplayOrder.AS_NEEDED,
            DisplayOrder.BOUNDS_DURATION,
            DisplayOrder.BOUNDS_PERIOD,
            DisplayOrder.BOUNDS_RANGE,
            DisplayOrder.COUNT_COUNT_MAX,
            DisplayOrder.EVENT,
            DisplayOrder.CODE,
            DisplayOrder.MAX_DOSE_PER_PERIOD,
            DisplayOrder.MAX_DOSE_PER_ADMINISTRATION,
            DisplayOrder.MAX_DOSE_PER_LIFETIME,
            DisplayOrder.ADDITIONAL_INSTRUCTION,
            DisplayOrder.PATIENT_INSTRUCTION
    );
    // Override separator between each part of "Dosage"
    @Builder.Default private String displaySeparator = " - ";
    /**
     * Function to turn a quantity unit (e.g UCUM "ml") into a string for humans (e.g "militier")
     * The choice to handle plural form or not is thus under the hands of people ;)
     */
    @Builder.Default private Function<Quantity, CompletableFuture<String>> fromFHIRQuantityUnitToString = DefaultImplementations::fromFHIRQuantityUnitToString;
    /**
     * Function to turn a codeable concept (e.g SNOMED CT "311504000") into a string for humans (e.g "With or after food")
     * The choice to handle extension, local valueset, ... is thus under the hands of people ;)
     */
    @Builder.Default private Function<CodeableConcept, CompletableFuture<String>> fromCodeableConceptToString = DefaultImplementations::fromCodeableConceptToString;
}
