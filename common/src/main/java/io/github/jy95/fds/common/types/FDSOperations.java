package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * A version-independent interface for FHIR operations.
 * Subclasses must implement methods for specific FHIR versions.
 *
 * @param <TQuantity> The type representing Quantity in the FHIR version.
 * @param <TCodeableConcept> The type representing CodeableConcept in the FHIR version.
 * @param <TExtension> The type representing Extension in the FHIR version.
 * @param <TDosage> The type representing Dosage in the FHIR version.
 * @param <TDoseAndRateComponent> The type representing DoseAndRateComponent in the FHIR version.
 * @param <TType> The type representing a FHIR Type (Quantity, Duration, ...).
 * @author jy95
 */
public interface FDSOperations<
        TQuantity,
        TCodeableConcept,
        TExtension,
        TDosage,
        TDoseAndRateComponent,
        TType
> {

    /**
     * Converts a FHIR Quantity object to a string representation of its unit or code.
     *
     * @param quantity the Quantity object to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the unit or code of the Quantity
     */
    CompletableFuture<String> fromFHIRQuantityUnitToString(TQuantity quantity);

    /**
     * Converts a FHIR CodeableConcept to a string representation.
     *
     * @param codeableConcept the CodeableConcept to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the text, display, or code of the first coding
     */
    CompletableFuture<String> fromCodeableConceptToString(TCodeableConcept codeableConcept);

    /**
     * Converts a list of FHIR Extension objects to a JSON-like string representation.
     *
     * @param extensions the list of Extension objects to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to a JSON-like string representing the extensions.
     */
    CompletableFuture<String> fromExtensionsToString(List<TExtension> extensions);

    /**
     * Selects a specific dosage and rate field from a list of Dosage.DosageDoseAndRateComponent.
     *
     * @param doseAndRateComponentList the list of dosage components.
     * @param doseAndRateKey the key used to extract the specific field.
     * @return the extracted Type value.
     */
    TType selectDosageAndRateField(List<TDoseAndRateComponent> doseAndRateComponentList, DoseAndRateKey doseAndRateKey);

    /**
     * <p>hasMatchingComponent.</p>
     *
     * @param dosage a TDosage object
     * @param predicate a {@link java.util.function.Predicate} object
     * @return a boolean
     */
    boolean hasMatchingComponent(TDosage dosage, Predicate<TDoseAndRateComponent> predicate);
}
