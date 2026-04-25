package io.github.jy95.fds.common.translators.timing.repeat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.instance.model.api.IPrimitiveType;

/**
 * Generic class for translating "timing.repeat.timeOfDay" across different FHIR versions.
 *
 * @param <D> The type of the data object containing the timeOfDay field.
 * @param <C> The type of the FDSConfig used for translation.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class TimeOfDay<D, C extends FDSConfig> implements Translator<D> {
    
    /** Translation service */
    private final TranslationService<C> translationService;
    /** Function to extract the list of timeOfDay values from the data object */
    private final Function<D, List<? extends IPrimitiveType<String>>> extractor;
    /** Predicate to check the presence of timeOfDay values in the data object */
    private final Predicate<D> presence;

    @Override
    public CompletableFuture<String> convert(D data) {
        // Capture the message outside the async block for consistency
        var timeOfDayMsg = translationService.getMessage("fields.timeOfDay");

        return CompletableFuture.supplyAsync(() -> {
            var times = getTimes(data);
            var formattedTimes = times.stream().map(this::formatString).toList();
            var timeOfDaysAsString = ListToString.convert(translationService, formattedTimes);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", formattedTimes.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }

    /**
     * Extracts a list of time values from the data object.
     *
     * @param data The data object containing time values.
     * @return A list of times represented as strings in the format hh:mm:ss.
     */
    private List<String> getTimes(D data) {
        return extractor.apply(data)
            .stream()
            .map(IPrimitiveType::getValue)
            .toList();
    }

    /**
     * Formats a time string to remove unnecessary seconds if they are zero.
     * The input must follow the format hh:mm:ss, where seconds may be ignored if
     * zero.
     *
     * @param time A time string in the format hh:mm:ss.
     * @return A formatted time string with optional seconds removed.
     */
    private String formatString(String time) {
        return time.replaceFirst("^(\\d{2}:\\d{2}):00$", "$1");
    }

    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }

}
