package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.boundsPeriod".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface BoundsPeriod<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "boundsPeriod" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "boundsPeriod"
     */
    default MessageFormat getBoundsPeriodMsg(ResourceBundle bundle, Locale locale) {
        String msg = bundle.getString("fields.boundsPeriod");
        return new MessageFormat(msg, locale);
    }

    /**
     * Extract information about the boundsPeriod
     *
     * @param dosage The dosage object to analyze
     * @return A map
     */
    default Map<String, Object> extractInformation(D dosage) {
        // Check conditions
        var hasStart = hasStartPeriod(dosage);
        var hasEnd = hasEndPeriod(dosage);

        // Prepare date values using FormatDateTimes.convert()
        String startDate = hasStart ? formatStartPeriod(dosage) : "";
        String endDate = hasEnd ? formatEndPeriod(dosage) : "";

        // Choose the correct condition based on the presence of start and end dates
        String condition = hasStart && hasEnd ? "0" : (hasStart ? "1" : "other");

        // Create a map of named arguments
        return Map.of(
                "startDate", startDate,
                "endDate", endDate,
                "condition", condition
        );
    }

    /**
     * Check if dosage has a "start" period
     *
     * @param dosage the dosage object to check
     * @return True if it is the case, otherwise false
     */
    boolean hasStartPeriod(D dosage);

    /**
     * Check if dosage has an "end" period
     *
     * @param dosage the dosage object to check
     * @return True if it is the case, otherwise false
     */
    boolean hasEndPeriod(D dosage);

    /**
     * Format start period to a human-readable string
     *
     * @param dosage the dosage field to be converted
     * @return the formatted start period as a string (e.g., "from May 23, 2011")
     */
    String formatStartPeriod(D dosage);

    /**
     * Format end period to a human-readable string
     *
     * @param dosage the dosage field to be converted
     * @return the formatted end period as a string (e.g., "to Feb 7, 2015")
     */
    String formatEndPeriod(D dosage);
}
