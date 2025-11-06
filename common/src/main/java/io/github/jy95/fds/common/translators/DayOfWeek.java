package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

/**
 * Interface for translating "timing.repeat.dayOfWeek".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface DayOfWeek<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    // Key constant for dayOfWeek message
    String KEY_DAY_OF_WEEK = "fields.dayOfWeek";

}
