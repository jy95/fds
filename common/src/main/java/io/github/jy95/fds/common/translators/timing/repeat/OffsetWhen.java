package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.offset" / "timing.repeat.when".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface OffsetWhen<D> extends TranslatorTiming<D> {

    /** Constant <code>order</code> */
    List<String> order = List.of("d", "h", "min");

    /**
     * Extracts the time components (days, hours, minutes) from a given offset in
     * minutes.
     *
     * @param minutes The offset in minutes to be converted into days, hours, and
     *                minutes.
     * @return A {@link java.util.Map} with keys "d" (days), "h" (hours), and "min"
     *         (minutes), and their corresponding values.
     */
    default Map<String, Integer> extractTime(int minutes) {
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
     * @param translationService a
     *                           {@link io.github.jy95.fds.common.functions.TranslationService}
     *                           object
     * @param offset             The offset in minutes to be converted.
     * @return A {@link java.util.concurrent.CompletableFuture} containing the
     *         formatted string representing the offset.
     * @since 2.1.1
     */
    default CompletableFuture<String> turnOffsetValueToText(TranslationService<?> translationService, int offset) {
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
