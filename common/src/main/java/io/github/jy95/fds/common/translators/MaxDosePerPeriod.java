package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "maxDosePerPeriod".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface MaxDosePerPeriod<C extends FDSConfig, D> extends Translator<D> {

    // Key constant for maxDosePerPeriod message
    String KEY_MAX_DOSE_PER_PERIOD = "fields.maxDosePerPeriod";

}
