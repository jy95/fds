package io.github.jy95.fds.common.operations;

import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.instance.model.api.IBase;

/**
 * Interface for FHIR Quantity-related conversion operations.
 *
 * @param <Q> The type representing Quantity in the FHIR version.
 * @since 2.1.4
 */
public interface QuantityProcessor<Q extends IBase> {
    /**
     * Converts a FHIR Quantity object to a string representation of its unit or
     * code.
     *
     * @param quantity the Quantity object to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the
     *         unit or code of the Quantity
     */
    CompletableFuture<String> fromFHIRQuantityUnitToString(Q quantity);
}