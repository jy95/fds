package io.github.jy95.fds.common.translators;

import org.hl7.fhir.instance.model.api.IBaseHasExtensions;
import io.github.jy95.fds.common.types.Translator;

/**
 * An interface for translating "extension".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.4
 */
public interface Extension<D extends IBaseHasExtensions> extends Translator<D> {

    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D dosage) {
        return dosage.hasExtension();
    }
}
