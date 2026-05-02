package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.instance.model.api.IBaseEnumeration;
import org.hl7.fhir.instance.model.api.IPrimitiveType;

/**
 * Translator for translating "timing.repeat.offset" / "timing.repeat.when".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class OffsetWhen<D, C extends FDSConfig, V extends IBaseEnumeration<?>> implements Translator<D> {

    /** Translation service */
    private final TranslationService<C> translationService;

    /** Constant <code>order</code> */
    private List<String> order = List.of("d", "h", "min");

    private final Function<D, List<V>>  whenExtractor;  
    private final Predicate<D>          whenPresence;  
    private final Function<D, Integer>  offsetExtractor;  
    private final Predicate<D>          offsetPresence;  

    @Override
    public boolean isPresent(D data) {
        // Rule: If there's an offset, there must be a when (and not C, CM, CD, CV)
        return offsetPresence.test(data) || whenPresence.test(data);
    }

    @Override
    public CompletableFuture<String> convert(D data) {
        var offsetPart = turnOffsetToText(data);
        var whenPart = turnWhenToText(data);

        return offsetPart.thenCombineAsync(whenPart,(offsetText, whenText) -> Stream
                .of(offsetText, whenText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /**
     * Converts the offset part of the timing to a text representation.
     *
     * @param data The timing data.
     * @return A {@link java.util.concurrent.CompletableFuture} containing the offset text.
     */
    private CompletableFuture<String> turnOffsetToText(D data) {
        if (!offsetPresence.test(data)) {
            return CompletableFuture.completedFuture("");
        }

        int offsetValue = offsetExtractor.apply(data);
        return turnOffsetValueToText(offsetValue);
    }

    /**
     * Converts the "when" part of the timing to a text representation.
     *
     * @param data The timing data.
     * @return A {@link java.util.concurrent.CompletableFuture} containing the "when" text.
     */
    private CompletableFuture<String> turnWhenToText(D data) {

        if (!whenPresence.test(data)) {
            return CompletableFuture.completedFuture("");
        }

        return CompletableFuture.supplyAsync(() -> {
            var events = whenExtractor
                    .apply(data)
                    .stream()
                    .map(IPrimitiveType::getValueAsString)
                    .map(translationService::getText)
                    .toList();

            return ListToString.convert(translationService, events);
        });
    }

    /**
     * Extracts the time components (days, hours, minutes) from a given offset in
     * minutes.
     *
     * @param minutes The offset in minutes to be converted into days, hours, and
     *                minutes.
     * @return A {@link java.util.Map} with keys "d" (days), "h" (hours), and "min"
     *         (minutes), and their corresponding values.
     */
    Map<String, Integer> extractTime(int minutes) {
        int d = minutes / 1440;
        int h = (minutes % 1440) / 60;
        int min = minutes % 60;

        return Map.of(
                "d", d,
                "h", h,
                "min", min);
    }

    /**
     * Converts an offset value (in minutes) into a human-readable time string.
     * The result combines the extracted time components (days, hours, minutes) into
     * a formatted string.
     *
     * @param offset             The offset in minutes to be converted.
     * @return A {@link java.util.concurrent.CompletableFuture} containing the
     *         formatted string representing the offset.
     * @since 2.1.1
     */
    private CompletableFuture<String> turnOffsetValueToText(int offset) {
        return CompletableFuture.supplyAsync(() -> {
            var extractedTime = extractTime(offset);
            var locale = translationService.getConfig().getLocale();

            var times = order
                    .stream()
                    .filter(unit -> extractedTime.getOrDefault(unit, 0) > 0)
                    .map(unit -> {
                        var amount = extractedTime.get(unit);
                        return UnitsOfTimeFormatter.formatWithCount(locale, unit, amount);
                    })
                    .toList();

            return ListToString.convert(translationService, times);
        });
    }

}
