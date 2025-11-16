package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "doseAndRate.rateRange".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface RateRange<D> extends Translator<D> {

    /**
     * Key constant for rateRange message
     */
    String KEY_RATE_RANGE = "fields.rateRange";

}
