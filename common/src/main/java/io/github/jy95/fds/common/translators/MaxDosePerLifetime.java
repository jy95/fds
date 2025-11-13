package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

/**
 * Interface for translating "maxDosePerLifetime".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface MaxDosePerLifetime<C extends FDSConfig, D> extends Translator<D> {

    // Key constant for maxDosePerLifetime message
    String KEY_MAX_DOSE_PER_LIFETIME = "fields.maxDosePerLifetime";
    
}
