package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import org.hl7.fhir.instance.model.api.IBaseExtension;
import org.hl7.fhir.instance.model.api.IBaseHasExtensions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.ExtensionProcessor;
import lombok.Builder;
import lombok.Value;


/**
 * A translator for converting FHIR extensions to strings.
 *
 * @param <T> The type of the data being translated, which must implement IBase.
 * @param <E> The type of the extension being translated, which must implement IBaseExtension.
 * @param <C> The type of the configuration used for translation, which must extend FDSConfig and implement ExtensionProcessor.
 * @author jy95
 * @since 2.1.9
 */
@Builder
@Value
public class ExtensionTranslator<
    T extends IBaseHasExtensions,
    E extends IBaseExtension<E, ?>,
    C extends FDSConfig & ExtensionProcessor<E>
> implements Translator<T> {
    
    /* TranslationService for handling the translation of extensions */
    private final TranslationService<C> translationService;
    /* Function to extract the list of extensions from the data */
    @Builder.Default
    private final Function<T, List<E>> extractor = T::getExtension;
    /* Function to check if extensions are present in the data */
    @Builder.Default
    private final Function<T, Boolean> presence = T::hasExtension;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(T data) {
        return translationService
                .getConfig()
                .fromExtensionsToString(
                        extractor.apply(data)
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(T data) {
        return presence.apply(data);
    }

}
