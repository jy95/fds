package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "timing.repeat.timeOfDay".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractTimeOfDay<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "timeOfDay" translation. */
    protected final MessageFormat timeOfDayMsg;

    /**
     * Constructor for {@code AbstractTimeOfDay}.
     *
     * @param config The configuration object used for translation.
     */
    public AbstractTimeOfDay(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.timeOfDay");
        timeOfDayMsg = new MessageFormat(msg, locale);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var bundle = this.getResources();
            var times = getTimes(dosage);
            var timeOfDays = times.stream().map(this::formatString).toList();
            var timeOfDaysAsString = ListToString.convert(bundle, timeOfDays);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", timeOfDays.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }

    /**
     * Extracts a list of time values from the data object.
     *
     * @param dosage The data object containing time values.
     * @return A list of times represented as strings in the format hh:mm:ss.
     */
    protected abstract List<String> getTimes(D dosage);

    /**
     * Formats a time string to remove unnecessary seconds if they are zero.
     * The input must follow the format hh:mm:ss, where seconds may be ignored if zero.
     *
     * @param time A time string in the format hh:mm:ss.
     * @return A formatted time string with optional seconds removed.
     */
    protected String formatString(String time) {
        String[] parts = time.split(":");

        if (parts.length > 2 && parts[2].equals("00")) {
            parts = new String[] { parts[0], parts[1] };
        }

        return String.join(":", parts);
    }
}
