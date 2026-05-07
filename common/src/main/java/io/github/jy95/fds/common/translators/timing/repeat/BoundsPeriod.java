package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.PeriodToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

/**
 * Translator for converting a boundsPeriod into a human-readable string format.
 * This class utilizes a translation service to fetch localized messages and a period-to-string converter
 * to handle the conversion of the period value. It also checks for the presence of the boundsPeriod field
 * in the data before attempting conversion.
 *
 * @param <D> The type of data being translated.
 * @param <P> The type of period being processed, which must implement IBase.
 * @param <C> The type of configuration used for processing periods, which must extend FDSConfig.
 * 
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class BoundsPeriod<
D,
P extends IBase,
C extends FDSConfig
> implements Translator<D> {

    /* Translation service for handling translation logic */
    private final TranslationService<C> translationService;
    /* PeriodToString converter for converting the period to a human-readable string */
    private final PeriodToString<P, ?, C> periodToString;
    /* Predicate to check the presence of the boundsPeriod field in the data */
    private final Predicate<D> presenceChecker;
    /* Function to extract the boundsPeriod from the data */
    private final Function<D, P> extractor;

    @Override
    public boolean isPresent(D data) {
        return presenceChecker.test(data);
    }

    @Override
    public CompletableFuture<String> convert(D data) {
        var period = extractor.apply(data);
        return periodToString.convert(translationService, period);
    }
}
