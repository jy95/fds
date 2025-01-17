package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.timeOfDay".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface TimeOfDay<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "timeOfDay" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "timeOfDay"
     */
    default MessageFormat getTimeOfDayMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.timeOfDay");
        return new MessageFormat(msg, locale);
    }

    /**
     * Extracts a list of time values from the data object.
     *
     * @param dosage The data object containing time values.
     * @return A list of times represented as strings in the format hh:mm:ss.
     */
    List<String> getTimes(D dosage);

    /**
     * Formats a time string to remove unnecessary seconds if they are zero.
     * The input must follow the format hh:mm:ss, where seconds may be ignored if zero.
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
