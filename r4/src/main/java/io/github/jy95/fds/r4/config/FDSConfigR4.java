package io.github.jy95.fds.r4.config;

import io.github.jy95.fds.common.config.FDSConfig;

import io.github.jy95.fds.common.types.DoseAndRateKey;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;

/**
 * R4 Configuration class for controlling how dosage are handled and displayed.
 */
@Getter
@SuperBuilder
public class FDSConfigR4 extends FDSConfig {
    /**
     * Function to turn a quantity unit (e.g UCUM "ml") into a string for humans (e.g "militier")
     * The choice to handle plural form or not is thus under the hands of people
     */
    @Builder.Default private Function<Quantity, CompletableFuture<String>> fromFHIRQuantityUnitToString = DefaultImplementationsR4::fromFHIRQuantityUnitToString;
    /**
     * Function to turn a codeable concept (e.x SNOMED CT "311504000") into a string for humans (e.g "With or after food")
     * The choice to handle extension, local valueset, ... is thus under the hands of people
     */
    @Builder.Default private Function<CodeableConcept, CompletableFuture<String>> fromCodeableConceptToString = DefaultImplementationsR4::fromCodeableConceptToString;
    /**
     * Function to turn extension(s) into a string for humans.
     * The choice to handle national extensions, ... is thus under the hands of people
     */
    @Builder.Default private Function<List<Extension>, CompletableFuture<String>> fromExtensionsToString = DefaultImplementationsR4::fromExtensionsToString;
    /**
     * Function to select the proper "doseAndRate" field of a given type from a list of "dosageAndRate"
     * Because of optional type element DoseAndRateType, it is possible to have "Calculated" and "Ordered" fields
     * Most of the time, what matter is only the first element, but in case of, this function give control
     * on the selection strategy
     * @see <a href="http://terminology.hl7.org/ValueSet/dose-rate-type">DoseAndRateType</a>
     */
    @Builder.Default private BiFunction<List<DosageDoseAndRateComponent>, DoseAndRateKey, Type> selectDosageAndRateField = DefaultImplementationsR4::selectDosageAndRateField;
}
