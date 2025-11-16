package io.github.jy95.fds.common.operations;

import java.util.concurrent.CompletableFuture;
import org.hl7.fhir.instance.model.api.IBase;

/**
 * Interface for FHIR CodeableConcept-related conversion operations.
 *
 * @param <C> The type representing CodeableConcept in the FHIR version.
 * @since 2.1.4
 */
public interface CodeableConceptProcessor<C extends IBase> {
    /**
     * Converts a FHIR CodeableConcept to a string representation.
     *
     * @param codeableConcept the CodeableConcept to be converted.
     * @return a {@link java.util.concurrent.CompletableFuture} that resolves to the
     *         text, display, or code of the first coding
     */
    CompletableFuture<String> fromCodeableConceptToString(C codeableConcept);
}