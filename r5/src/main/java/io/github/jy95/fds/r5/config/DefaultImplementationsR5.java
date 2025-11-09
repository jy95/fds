package io.github.jy95.fds.r5.config;

import io.github.jy95.fds.common.config.DefaultImplementations;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.functions.DoseAndRateRegistryR5;
import org.hl7.fhir.r5.model.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * Provides FHIR R5 implementations for common operations in the library.
 *
 * @author jy95
 */
public final class DefaultImplementationsR5 {

    /**
     * No constructor for this class
     */
    private DefaultImplementationsR5(){}

    /**
     * Converts a FHIR {@link org.hl7.fhir.r5.model.Quantity} object to a string representation of its unit or code.
     *
     * @param quantity the {@link org.hl7.fhir.r5.model.Quantity} object to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the unit or code of the {@link org.hl7.fhir.r5.model.Quantity}
     */
    public static CompletableFuture<String> fromFHIRQuantityUnitToString(Quantity quantity) {
        return CompletableFuture.supplyAsync(() -> {

            if (Objects.isNull(quantity)) {
                return null;
            }

            if (Objects.nonNull(quantity.getCode())) {
                return quantity.getCode();
            }

            if (Objects.nonNull(quantity.getUnit())) {
                return quantity.getUnit();
            }

            return "";
        });
    }

    /**
     * Converts a FHIR {@link org.hl7.fhir.r5.model.CodeableConcept} to a string representation.
     *
     * @param codeableConcept the {@link org.hl7.fhir.r5.model.CodeableConcept} to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the text, display, or code of the first coding
     */
    public static CompletableFuture<String> fromCodeableConceptToString(CodeableConcept codeableConcept) {
        return CompletableFuture.supplyAsync(() -> {

            if (Objects.isNull(codeableConcept)) {
                return null;
            }

            if (Objects.nonNull(codeableConcept.getText())) {
                return codeableConcept.getText();
            }

            if (!codeableConcept.hasCoding()) {
                return null;
            }

            var firstCode = codeableConcept.getCodingFirstRep();
            return DefaultImplementations.fromCodingToString(firstCode);
        });
    }

    /**
     * Converts a list of FHIR {@link org.hl7.fhir.r5.model.Extension} objects to a JSON-like string representation.
     *
     * @param extensions the list of {@link org.hl7.fhir.r5.model.Extension} objects to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to a JSON-like string representing the extensions.
     */
    public static CompletableFuture<String> fromExtensionsToString(List<Extension> extensions) {
        return DefaultImplementations.fromExtensionsToString(extensions);
    }

    /**
     * Selects a specific dosage and rate field from a list of {@link org.hl7.fhir.r5.model.Dosage.DosageDoseAndRateComponent}.
     *
     * @param doseAndRateComponentList the list of dosage components.
     * @param doseAndRateKey the key used to extract the specific field.
     * @return the extracted {@link org.hl7.fhir.r5.model.DataType} value.
     */
    public static DataType selectDosageAndRateField(List<Dosage.DosageDoseAndRateComponent> doseAndRateComponentList, DoseAndRateKey doseAndRateKey) {
        var extractor = DoseAndRateRegistryR5.getInstance().getExtractor(doseAndRateKey);
        var firstRep = doseAndRateComponentList.get(0);
        return extractor.extract(firstRep);
    }

    /**
     * Checks if a {@link org.hl7.fhir.r5.model.Dosage} has any component matching a given predicate.
     *
     * @param dosage the {@link org.hl7.fhir.r5.model.Dosage} to check.
     * @param predicate the predicate to apply to each component.
     * @return {@code true} if any component matches the predicate; {@code false} otherwise.
     */
    public static boolean hasMatchingComponent(Dosage dosage, Predicate<Dosage.DosageDoseAndRateComponent> predicate) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(predicate);
    }
}
