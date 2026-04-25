package io.github.jy95.fds.common.types;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.DosageAndRateProcessor;
import lombok.RequiredArgsConstructor;

/**
 * Generic translator for dose and rate components in FHIR resources.
 *
 * @param <D> The type of the dosage resource (e.g., Dosage).
 * @param <E> The type of the component within the dosage (e.g., Dosage.DosageDoseAndRateComponent).
 * @param <C> The type of configuration object extending FDSConfig and DosageAndRateProcessor.
 * @param <T> The type of the specific field to be translated (e.g., Quantity, Ratio).
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class DoseAndRateTranslator<
D extends IBase,
E extends IBase,
C extends FDSConfig & DosageAndRateProcessor<D, E, ?>,
T extends IBase
> implements Translator<D> {
    
    /* TranslationService for handling the translation of extensions */
    private final TranslationService<C> translationService;
    /* Key to identify the specific field in the dosage and rate component */
    private final DoseAndRateKey field;
    /* Predicate to check if the specific component is present in the dosage */
    private final Predicate<E> predicate;
    /* Function to extract the list of components from the dosage */
    private final Function<D, List<E>> extractor;
    /* Converter to translate the specific field to a human-readable string */
    private final ValueConverter<C, T> valueConverter;

    /* Functional interface for converting specific field values to human-readable strings */
    @FunctionalInterface
    public interface ValueConverter<C extends FDSConfig, T> {
        /**
         * Converts a specific field value to a human-readable string.
         *
         * @param service The translation service providing access to configuration and messages.
         * @param value The specific field value to be converted.
         * @return A CompletableFuture that will complete with the translated string.
         */
        CompletableFuture<String> convert(TranslationService<C> service, T value);
    }
    
    @Override
    public CompletableFuture<String> convert(D data) {
        var config = translationService.getConfig();
        var component = config
            .selectDosageAndRateField(
                extractor.apply(data),
                field
            );

        return valueConverter
            .convert(translationService, (T) component)
            .thenApply(text -> {
                var messageKey = field.getMessageKey();
                if (messageKey != null) {
                    var message = translationService.getMessage(messageKey);
                    return message.format(new Object[]{text});
                }
                return text;
            });
    }

    @Override
    public boolean isPresent(D data) {
        return translationService
            .getConfig()
            .hasMatchingComponent(
                data,
                predicate
            );

    }
}
