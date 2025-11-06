package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.BoundsPeriod;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.FormatDateTimesR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.boundsPeriod"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsPeriodR5 implements BoundsPeriod<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasStartPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasStart();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasEndPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasEnd();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public String formatStartPeriod(Dosage dosage) {
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        var config = translationService.getConfig();
        var locale = config.getLocale();
        return FormatDateTimesR5.getInstance().convert(locale, boundPeriods.getStartElement());
    }

    /** {@inheritDoc} */
    @Override
    public String formatEndPeriod(Dosage dosage) {
        var config = translationService.getConfig();
        var locale = config.getLocale();
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        return FormatDateTimesR5.getInstance().convert(locale, boundPeriods.getEndElement());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var arguments = extractInformation(dosage);
            // Format the message with the named arguments
            var boundsPeriodMsg = translationService.getMessage(KEY_BOUNDS_PERIOD);
            return boundsPeriodMsg.format(arguments);
        });
    }
}
