package io.github.jy95.fds.common.translators.dosage;

import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "doseAndRate.rateQuantity".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface RateQuantity<D> extends Translator<D> {

    /**
     * Key constant for rateQuantity message
     */
    String KEY_RATE_QUANTITY = "fields.rateQuantity";

}
