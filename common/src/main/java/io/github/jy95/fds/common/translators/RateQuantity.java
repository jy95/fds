package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "doseAndRate.rateQuantity".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface RateQuantity<C extends FDSConfig, D> extends Translator<C, D> {

    // Key constant for rateQuantity message
    String KEY_RATE_QUANTITY = "fields.rateQuantity";

}
