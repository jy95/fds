package io.github.jy95.fds.common.translators;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

/**
 * An abstract class for translating "timing.repeat.offset" / "timing.repeat.when".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractOffsetWhen<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    /**
     * Constructor for {@code AbstractOffsetWhen}.
     * @param config The configuration object used for translation.
     */
    public AbstractOffsetWhen(C config) {
        super(config);
    }

    /**
     * Extracts the time components (days, hours, minutes) from a given offset in minutes.
     *
     * @param minutes The offset in minutes to be converted into days, hours, and minutes.
     * @return A {@link Map} with keys "d" (days), "h" (hours), and "min" (minutes), and their corresponding values.
     */
    protected Map<String, Integer> extractTime(int minutes) {
        int d = minutes / 1440;
        int h = (minutes % 1440) / 60;
        int min = minutes % 60;

        return Map.of(
                "d", d,
                "h", h,
                "min", min
        );
    }

    /**
     * Converts an offset value (in minutes) into a human-readable time string.
     * The result combines the extracted time components (days, hours, minutes) into a formatted string.
     *
     * @param offset The offset in minutes to be converted.
     * @return A {@link CompletableFuture} containing the formatted string representing the offset.
     */
    protected CompletableFuture<String> turnOffsetValueToText(int offset) { 
        return CompletableFuture.supplyAsync(() -> {
            var bundle = getResources();
            var extractedTime = extractTime(offset);
            List<String> order = List.of("d", "h", "min");

            var times = order
                    .stream()
                    .filter(unit -> extractedTime.getOrDefault(unit, 0) > 0)
                    .map(unit -> {
                        var unitMsg = bundle.getString("withCount." + unit);
                        var amount = extractedTime.get(unit);
                        return MessageFormat.format(unitMsg, amount);
                    })
                    .toList();

            return ListToString.convert(bundle, times);
        });
    }
}
