package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.instance.model.api.IBaseExtension;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.ExtensionProcessor;

/**
 * Interface for translators that specifically handle dosage objects with "extension" elements.
 * This class extends {@link io.github.jy95.fds.common.types.Translator} to provide additional reusable code for "extension"
 *
 * @param <D> the type of dosage field to be translated
 * @param <E> the type of extension field to be translated
 * @param <C> the type of config supporting the extension processing
 * @author jy95
 * @since 2.1.5
 */
public interface TranslatorExtension<D, E extends IBaseExtension<E, ?>, C extends FDSConfig & ExtensionProcessor<E>> extends Translator<D> {
    
    /**
     * Return a list of extension contained in the dosage field
     * 
     * @param dosage the dosage field to be converted
     * @return The list of extension
     */
    List<E> getExtension(D dosage);

    /**
     * Return the TranslationService responsible for handling extensions
     * 
     * @return the TranslationService
     */
    TranslationService<C> getTranslationService();

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D dosage) {
        var extensions = getExtension(dosage);
        return getTranslationService()
            .getConfig()
            .fromExtensionsToString(extensions);
    }
}
