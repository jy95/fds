package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

/**
 * Interface for translating "timing.repeat.count" / "timing.repeat.countMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface CountCountMax<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    // Key constant for countMax message
    String KEY_COUNT_MAX = "fields.countMax";
    // Key constant for count message
    String KEY_COUNT = "fields.count";

    /**
     * Check if "timing.repeat.countMax" exists
     *
     * @param dosage the dosage object to check
     * @return True if it is the case, false otherwise
     */
    boolean hasCountMax(D dosage);
}
