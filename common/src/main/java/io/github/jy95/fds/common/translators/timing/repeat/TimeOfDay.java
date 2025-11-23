package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

import java.util.List;

/**
 * Interface for translating "timing.repeat.timeOfDay".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface TimeOfDay<D> extends Translator<D> {

    /**
     * Key constant for timeOfDay message
     */
    String KEY_TIME_OF_DAY = "fields.timeOfDay";

    /**
     * Extracts a list of time values from the data object.
     *
     * @param dosage The data object containing time values.
     * @return A list of times represented as strings in the format hh:mm:ss.
     */
    List<String> getTimes(D dosage);

    /**
     * Formats a time string to remove unnecessary seconds if they are zero.
     * The input must follow the format hh:mm:ss, where seconds may be ignored if
     * zero.
     *
     * @param time A time string in the format hh:mm:ss.
     * @return A formatted time string with optional seconds removed.
     */
    default String formatString(String time) {
        String[] parts = time.split(":");

        if (parts.length > 2 && parts[2].equals("00")) {
            parts = new String[] { parts[0], parts[1] };
        }

        return String.join(":", parts);
    }
}
