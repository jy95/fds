package io.github.jy95.fds.common.types;

import io.github.jy95.fds.common.config.FDSConfig;

import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating dosage fields into human-readable representations.
 * This class provides base functionality for configuration and resource bundle handling,
 * while requiring subclasses to define specific translation behavior.
 *
 * @param <C> the type of configuration extending {@link io.github.jy95.fds.common.config.FDSConfig}
 * @param <D> the type of dosage field to be translated
 * @author jy95
 */
public interface Translator<C extends FDSConfig, D> {
    /**
     * Converts a dosage field into a human-readable string representation asynchronously.
     *
     * @param dosage the dosage field to be converted
     * @return a {@link java.util.concurrent.CompletableFuture} that will complete with the human-readable string
     */
    CompletableFuture<String> convert(D dosage);

    /**
     * Checks whether a dosage field is present and can be converted to a string.
     *
     * @param dosage the dosage field to check
     * @return {@code true} if the dosage field is present, {@code false} otherwise
     */
    default boolean isPresent(D dosage) {
        return false;
    }
}
