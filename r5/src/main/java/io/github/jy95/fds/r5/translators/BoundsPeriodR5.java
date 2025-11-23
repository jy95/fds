package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.BoundsPeriod;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.boundsPeriod"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class BoundsPeriodR5 implements BoundsPeriod<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        return data.hasBoundsPeriod();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasStartPeriod(TimingRepeatComponent data) {
        return data.getBoundsPeriod().hasStart();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasEndPeriod(TimingRepeatComponent data) {
        return data.getBoundsPeriod().hasEnd();
    }

    /** {@inheritDoc} */
    @Override
    public String formatStartPeriod(TimingRepeatComponent data) {
        var boundPeriods = data.getBoundsPeriod();
        var start = boundPeriods.getStartElement();
        return translationService.dateTimeToHumanDisplay(
            start.getValue(), 
            start.getTimeZone(), 
            start.getPrecision()
        );
    }

    /** {@inheritDoc} */
    @Override
    public String formatEndPeriod(TimingRepeatComponent data) {
        var boundPeriods = data.getBoundsPeriod();
        var end = boundPeriods.getEndElement();
        return translationService.dateTimeToHumanDisplay(
            end.getValue(), 
            end.getTimeZone(), 
            end.getPrecision()
        );
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent data) {
        return CompletableFuture.supplyAsync(() -> {
            var arguments = extractInformation(data);
            // Format the message with the named arguments
            var boundsPeriodMsg = translationService.getMessage(KEY_BOUNDS_PERIOD);
            return boundsPeriodMsg.format(arguments);
        });
    }
}
