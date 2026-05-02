package io.github.jy95.fds.common.translators.timing;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IPrimitiveType;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Generic class for translating "timing.event" across different FHIR versions.
 *
 * @param <D> The type of the data object containing timing events.
 * @param <C> The type of the FDS configuration.
 * @param <T> The type of the timing event elements
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class TimingEvent<
D, 
C extends FDSConfig,
T extends IPrimitiveType<Date>
> implements Translator<D> {
    
    /* The translation service used for translation. */
    private final TranslationService<C> translationService;
    /* Function to extract a list of timing events from the data object. */
    private final Function<D, List<T>> extractor;
    /* Predicate to check the presence of timing events in the data object. */
    private final Predicate<D> presence;
    /* Function to convert a timing event element to a human-readable string. */
    private final Function<T, String> dateTimeToHumanDisplay;
    
    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }

    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {
            var eventsList = getEvents(data);

            String eventsAsString = ListToString.convert(translationService, eventsList);
            var timingEventMsg = translationService.getMessage("fields.event");

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "eventCondition", eventsList.size(),
                    "event", eventsAsString
            );

            // Format the message with the named arguments
            return timingEventMsg.format(arguments);
        });
    }

    /**
     * Extracts a list of timing events from the data object.
     *
     * @param data The data object containing timing events.
     * @return A list of timing events represented as strings.
     */
    List<String> getEvents(D data) {

        return extractor
            .apply(data)
            .stream()
            .map(dateTimeToHumanDisplay)
            .toList();
    }

}
