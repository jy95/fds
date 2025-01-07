package jy95.fhir.r4.config;

import jy95.fhir.common.config.DefaultImplementations;
import jy95.fhir.common.config.FDSConfig;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;

@Getter
@SuperBuilder
public class FDSConfigR4 extends FDSConfig {
        /**
     * Function to turn a quantity unit (e.g UCUM "ml") into a string for humans (e.g "militier")
     * The choice to handle plural form or not is thus under the hands of people
     */
    @Builder.Default private Function<Quantity, CompletableFuture<String>> fromFHIRQuantityUnitToString = quantity -> DefaultImplementations.fromFHIRQuantityUnitToString(quantity);
    /**
     * Function to turn a codeable concept (e.x SNOMED CT "311504000") into a string for humans (e.g "With or after food")
     * The choice to handle extension, local valueset, ... is thus under the hands of people
     */
    @Builder.Default private Function<CodeableConcept, CompletableFuture<String>> fromCodeableConceptToString = codeableConcept -> DefaultImplementations.fromCodeableConceptToString(codeableConcept);
    /**
     * Function to turn extension(s) into a string for humans.
     * The choice to handle national extensions, ... is thus under the hands of people
     */
    @Builder.Default private Function<List<Extension>, CompletableFuture<String>> fromExtensionsToString = extensions -> DefaultImplementations.fromExtensionsToString(extensions);
}
