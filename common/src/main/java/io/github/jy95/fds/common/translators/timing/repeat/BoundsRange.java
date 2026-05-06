package io.github.jy95.fds.common.translators.timing.repeat;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.QuantityProcessor;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Translator for converting a boundsRange quantity into a human-readable string format.
 * This class utilizes a translation service to fetch localized messages and a range-to-string converter
 * to handle the conversion of the range value. It also checks for the presence of the boundsRange field
 * in the data before attempting conversion.
 *
 * @param <D> The type of data being translated.
 * @param <R> The type of range being processed, which must implement IBase.
 * @param <Q> The type of quantity being processed within the range, which must implement IBase.
 * @param <C> The type of configuration used for processing quantities, which must extend FDSConfig and implement QuantityProcessor.
 * 
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class BoundsRange<
D,
R extends IBase,
Q extends IBase,
C extends FDSConfig & QuantityProcessor<Q>
> implements Translator<D> {


    /* Translation service for handling translation logic */
    private final TranslationService<C> translationService;
    /* Range to string converter for converting the boundsRange quantity into a human-readable format */
    private final RangeToString<R, Q, C> rangeToString;
    /* Predicate to check the presence of the boundsRange field in the data */
    private final Predicate<D> presenceChecker;
    /* Function to extract the boundsRange quantity from the data */
    private final Function<D, R> extractor;

    @Override
    public CompletableFuture<String> convert(D data) {
        
        var boundsRange = extractor.apply(data);
        var boundsRangeMsg = translationService.getMessage("fields.boundsRange");

        return rangeToString
            .convert(translationService, boundsRange)
            .thenApplyAsync((rangeText) -> boundsRangeMsg.format(new Object[]{rangeText}));
    }

    @Override
    public boolean isPresent(D data) {
        return presenceChecker.test(data);
    }

}
