package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "timing.repeat.dayOfWeek".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface DayOfWeek<D> extends Translator<D> {

    /**
     * Key constant for dayOfWeek message
     */
    String KEY_DAY_OF_WEEK = "fields.dayOfWeek";

}
