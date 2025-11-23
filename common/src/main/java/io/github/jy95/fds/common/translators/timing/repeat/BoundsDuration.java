package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "timing.repeat.boundsDuration".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface BoundsDuration<D> extends Translator<D> {

    /**
     * Key constant for boundsDuration message
     */
    String KEY_BOUNDS_DURATION = "fields.boundsDuration";

}
