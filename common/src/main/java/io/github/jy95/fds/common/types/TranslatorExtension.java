package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IBaseHasExtensions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.ExtensionProcessor;

/**
 * Interface for translators that specifically handle dosage objects with
 * "extension" elements.
 * This class extends {@link io.github.jy95.fds.common.types.Translator} to
 * provide additional reusable code for "extension"
 *
 * @param <D> the type of data to be translated
 * @param <E> the type of extension field to be translated
 * @param <C> the type of config supporting the extension processing
 * @author jy95
 * @since 2.1.5
 */
public interface TranslatorExtension<D extends IBaseHasExtensions, E extends IBaseExtension<E, ?>, C extends FDSConfig & ExtensionProcessor<E>>
        extends Translator<D> {

    /**
     * Return a list of extension contained in the data
     * 
     * @param data the data to be converted
     * @return The list of extension
     */
    List<E> getExtension(D data);

    /**
     * Return the TranslationService responsible for handling extensions
     * 
     * @return the TranslationService
     */
    TranslationService<C> getTranslationService();

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D data) {
        var extensions = getExtension(data);
        return getTranslationService()
                .getConfig()
                .fromExtensionsToString(extensions);
    }
}
