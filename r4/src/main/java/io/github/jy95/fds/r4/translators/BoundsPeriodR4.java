package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.BoundsPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsPeriod"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsPeriodR4 implements BoundsPeriod<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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
        var start = boundPeriods.getStartElement();
        return translationService.dateTimeToHumanDisplay(
            start.getValue(), 
            start.getTimeZone(), 
            start.getPrecision()
        );
    }

    /** {@inheritDoc} */
    @Override
    public String formatEndPeriod(Dosage dosage) {
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        var end = boundPeriods.getEndElement();
        return translationService.dateTimeToHumanDisplay(
            end.getValue(), 
            end.getTimeZone(), 
            end.getPrecision()
        );
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
