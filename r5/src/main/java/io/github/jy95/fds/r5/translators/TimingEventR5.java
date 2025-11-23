package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.TimingEvent;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.event"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingEventR5 implements TimingEvent<Timing> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Timing data) {
        return data.hasEvent();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getEvents(Timing data) {
        var events = data.getEvent();
        return events
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
    public CompletableFuture<String> convert(Timing data) {
        var timingEventMsg = translationService.getMessage(KEY_EVENT);

        return CompletableFuture.supplyAsync(() -> {
            var eventsList = getEvents(data);

            String eventsAsString = ListToString.convert(translationService, eventsList);

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
