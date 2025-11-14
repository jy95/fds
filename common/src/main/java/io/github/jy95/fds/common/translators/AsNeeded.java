package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.Translator;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating fields related to the "as needed" / "as needed for" concepts.
 *
 * @param <D> the type of data to be translated
 * @author jy95
 * @since 1.0.0
 */
public interface AsNeeded<D> extends Translator<D> {

    // Key constant for asNeededFor message
    String KEY_AS_NEEDED_FOR = "fields.asNeededFor";
    // Key constant for asNeeded message
    String KEY_AS_NEEDED = "fields.asNeeded";

    /**
     * Check if "as needed" is expressed with CodeableConcept ("asNeededFor" / "asNeededCodeableConcept")
     *
     * @param dosage The dosage to check
     * @return true if it is the case, otherwise false
     */
    boolean hasCodeableConcepts(D dosage);

    /**
     * Turn CodeableConcept(s) to a human-readable string
     *
     * @param dosage the dosage field to be converted
     * @return a {@link java.util.concurrent.CompletableFuture} that will complete with the human-readable string
     */
    CompletableFuture<String> convertCodeableConcepts(D dosage);
}
