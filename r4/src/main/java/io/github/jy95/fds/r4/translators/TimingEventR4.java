package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.TimingEvent;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.event"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingEventR4 implements TimingEvent<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasEvent();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getEvents(Dosage dosage) {
        return dosage
            .getTiming()
            .getEvent()
            .stream()
            .map(event -> translationService.dateTimeToHumanDisplay(
                event.getValue(), 
                event.getTimeZone(), 
                event.getPrecision()
            ))
            .toList();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var eventsList = getEvents(dosage);

            String eventsAsString = ListToString.convert(translationService, eventsList);
            var timingEventMsg = translationService.getMessage(KEY_EVENT);

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "eventCondition", eventsList.size(),
                    "event", eventsAsString
            );

            // Format the message with the named arguments
            return timingEventMsg.format(arguments);
        });
    }
}
