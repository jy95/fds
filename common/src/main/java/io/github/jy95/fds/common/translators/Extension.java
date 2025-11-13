package io.github.jy95.fds.common.translators;

import org.hl7.fhir.instance.model.api.IBaseHasExtensions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

/**
 * An interface for translating "extension".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.4
 */
public interface Extension<C extends FDSConfig, D extends IBaseHasExtensions> extends Translator<D> {

    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D dosage) {
        return dosage.hasExtension();
    }
}
