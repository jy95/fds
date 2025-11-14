package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;

/**
 * Interface for translating "timing.repeat.boundsRange".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface BoundsRange<D> extends TranslatorTiming<D> {

    // Key constant for boundsRange message
    String KEY_BOUNDS_RANGE = "fields.boundsRange";

}
