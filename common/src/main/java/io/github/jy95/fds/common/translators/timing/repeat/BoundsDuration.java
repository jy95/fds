package io.github.jy95.fds.common.translators.timing.repeat;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.QuantityProcessor;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Translator for converting a boundsDuration quantity into a human-readable string format.
 * This class utilizes a translation service to fetch localized messages and a quantity-to-string converter
 * to handle the conversion of the quantity value. It also checks for the presence of the boundsDuration field
 * in the data before attempting conversion.
 *
 * @param <D> The type of data being translated.
 * @param <Q> The type of quantity being processed, which must implement IBase.
 * @param <C> The type of configuration used for processing quantities, which must extend FDSConfig and implement QuantityProcessor.
 */
@RequiredArgsConstructor
public class BoundsDuration<
D, 
Q extends IBase, 
C extends FDSConfig & QuantityProcessor<Q>
> implements Translator<D> {

    /* Translation service for handling translation logic */
    private final TranslationService<C> translationService;
    /* Configuration object for processing quantities */
    private final QuantityToString<Q, C> quantityToString;
    /* Predicate to check the presence of the boundsDuration field in the data */
    private final Predicate<D> presenceChecker;
    /* Function to extract the boundsDuration quantity from the data */
    private final Function<D, Q> extractor;

    @Override
    public CompletableFuture<String> convert(D data) {

        var boundsDuration = extractor.apply(data);
        var boundsDurationMsg = translationService.getMessage("fields.boundsDuration");

        return quantityToString
            .convert(translationService, boundsDuration)
            .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }

    @Override
    public boolean isPresent(D data) {
        return presenceChecker.test(data);
    }

}
