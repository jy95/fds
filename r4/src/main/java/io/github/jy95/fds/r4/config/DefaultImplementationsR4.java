package io.github.jy95.fds.r4.config;

import io.github.jy95.fds.common.config.DefaultImplementations;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.common.types.DoseAndRateExtractor;
import org.hl7.fhir.r4.model.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * Provides FHIR R4 implementations for common operations in the library.
 *
 * @author jy95
 */
public final class DefaultImplementationsR4 {

    // Mapping of DoseAndRateKey to corresponding extractor functions
    public static final Map<DoseAndRateKey, DoseAndRateExtractor<Dosage.DosageDoseAndRateComponent, Type>> EXTRACTOR_MAP = Map.of(
        DoseAndRateKey.DOSE_QUANTITY, Dosage.DosageDoseAndRateComponent::getDoseQuantity,
        DoseAndRateKey.DOSE_RANGE, Dosage.DosageDoseAndRateComponent::getDoseRange,
        DoseAndRateKey.RATE_QUANTITY, Dosage.DosageDoseAndRateComponent::getRateQuantity,
        DoseAndRateKey.RATE_RANGE, Dosage.DosageDoseAndRateComponent::getRateRange,
        DoseAndRateKey.RATE_RATIO, Dosage.DosageDoseAndRateComponent::getRateRatio
    );

    /**
     * No constructor for this class
     */
    private DefaultImplementationsR4(){}

    /**
     * Converts a FHIR {@link org.hl7.fhir.r4.model.Quantity} object to a string representation of its unit or code.
     *
     * @param quantity the {@link org.hl7.fhir.r4.model.Quantity} object to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the unit or code of the {@link org.hl7.fhir.r4.model.Quantity}
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
     * Converts a FHIR {@link org.hl7.fhir.r4.model.CodeableConcept} to a string representation.
     *
     * @param codeableConcept the {@link org.hl7.fhir.r4.model.CodeableConcept} to be converted.
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
     * Checks if a {@link org.hl7.fhir.r4.model.Dosage} has any component matching a given predicate.
     *
     * @param dosage the {@link org.hl7.fhir.r4.model.Dosage} to check.
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
