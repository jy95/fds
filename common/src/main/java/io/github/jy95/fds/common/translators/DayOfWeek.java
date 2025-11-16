package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;

/**
 * Interface for translating "timing.repeat.dayOfWeek".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface DayOfWeek<D> extends TranslatorTiming<D> {

    /**
     * Key constant for dayOfWeek message
     */
    String KEY_DAY_OF_WEEK = "fields.dayOfWeek";

}
