package io.github.jy95.fds.common.operations;

import java.util.List;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;
import io.github.jy95.fds.common.types.DoseAndRateKey;

/**
 * Interface for DosageDoseAndRateComponent selection.
 *
 * @param <D> The type representing Dosage in the FHIR version.
 * @param <R> The type representing DoseAndRateComponent in the FHIR version.
 * @param <T> The type representing a FHIR Type (Quantity, Duration, ...).
 * @since 2.1.4
 */
public interface DosageAndRateProcessor<D extends IBase, R extends IBase, T extends IBase> {
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
