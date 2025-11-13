package io.github.jy95.fds.common.translators;

import org.hl7.fhir.instance.model.api.IBaseHasModifierExtensions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

/**
 * An interface for translating "modifierExtension".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.4
 */
public interface ModifierExtension<C extends FDSConfig, D extends IBaseHasModifierExtensions> extends Translator<D> {
    
    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D dosage) {
        return dosage.hasModifierExtension();
    }
}
