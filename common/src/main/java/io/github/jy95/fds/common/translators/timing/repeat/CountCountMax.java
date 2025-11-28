package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "timing.repeat.count" / "timing.repeat.countMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface CountCountMax<D> extends Translator<D> {

    /**
     * Key constant for countMax message
     */
    String KEY_COUNT_MAX = "fields.countMax";
    /**
     * Key constant for count message
     */
    String KEY_COUNT = "fields.count";

    /**
     * Check if "timing.repeat.countMax" exists
     *
     * @param data the data object to check
     * @return True if it is the case, false otherwise
     */
    boolean hasCountMax(D data);
}
