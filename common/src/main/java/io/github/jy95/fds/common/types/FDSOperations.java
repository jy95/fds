package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IBaseBackboneElement;

/**
 * A version-independent interface for FHIR operations.
 * Subclasses must implement methods for specific FHIR versions.
 *
 * @param <Q> The type representing Quantity in the FHIR version.
 * @param <C> The type representing CodeableConcept in the FHIR version.
 * @param <E> The type representing Extension in the FHIR version.
 * @param <D> The type representing Dosage in the FHIR version.
 * @param <R> The type representing DoseAndRateComponent in the FHIR version.
 * @param <T> The type representing a FHIR Type (Quantity, Duration, ...).
 * @author jy95
 * @since 1.0.0
 */
public interface FDSOperations<
        Q extends IBase,
        C extends IBase,
        E extends IBaseExtension<?, ?>,
        D extends IBaseBackboneElement,
        R extends IBase,
        T extends IBase
> {

    /**
     * Converts a FHIR Quantity object to a string representation of its unit or code.
     *
     * @param quantity the Quantity object to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the unit or code of the Quantity
     */
    CompletableFuture<String> fromFHIRQuantityUnitToString(Q quantity);

    /**
     * Converts a FHIR CodeableConcept to a string representation.
     *
     * @param codeableConcept the CodeableConcept to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the text, display, or code of the first coding
     */
    CompletableFuture<String> fromCodeableConceptToString(C codeableConcept);

    /**
     * Converts a list of FHIR Extension objects to a JSON-like string representation.
     *
     * @param extensions the list of Extension objects to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to a JSON-like string representing the extensions.
     */
    CompletableFuture<String> fromExtensionsToString(List<E> extensions);

    /**
     * Selects a specific dosage and rate field from a list of Dosage.DosageDoseAndRateComponent.
     *
     * @param doseAndRateComponentList the list of dosage components.
     * @param doseAndRateKey the key used to extract the specific field.
     * @return the extracted Type value.
     */
    T selectDosageAndRateField(List<R> doseAndRateComponentList, DoseAndRateKey doseAndRateKey);

    /**
     * Checks if a Dosage has any component matching a given predicate.
     *
     * @param dosage the Dosage to check
     * @param predicate the predicate to apply to each component.
     * @return {@code true} if any component matches the predicate; {@code false} otherwise.
     */
    boolean hasMatchingComponent(D dosage, Predicate<R> predicate);
}
