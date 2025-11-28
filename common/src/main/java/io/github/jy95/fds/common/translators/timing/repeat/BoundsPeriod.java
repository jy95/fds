package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.types.Translator;

import java.util.Map;

/**
 * Interface for translating "timing.repeat.boundsPeriod".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface BoundsPeriod<D> extends Translator<D> {

    /**
     * Key constant for boundsPeriod message
     */
    String KEY_BOUNDS_PERIOD = "fields.boundsPeriod";

    /**
     * Extract information about the boundsPeriod
     *
     * @param data The data object to analyze
     * @return A map
     */
    default Map<String, Object> extractInformation(D data) {
        // Check conditions
        var hasStart = hasStartPeriod(data);
        var hasEnd = hasEndPeriod(data);

        // Prepare date values using FormatDateTimes.convert()
        String startDate = GenericOperations.conditionalSelect(
            hasStart, 
            () -> formatStartPeriod(data), 
            () -> ""
        );

        String endDate = GenericOperations.conditionalSelect(
            hasEnd, 
            () -> formatEndPeriod(data), 
            () -> ""
        );

        // Choose the correct condition based on the presence of start and end dates
        String condition = getConditionCode(hasStart, hasEnd);

        // Create a map of named arguments
        return Map.of(
                "startDate", startDate,
                "endDate", endDate,
                "condition", condition);
    }

    /**
     * Determines the formatting condition code for the period.
     * 
     * @param hasStart  True if the period has a start date.
     * @param hasEnd True if the period has an end date.
     * @return The conditional code ("0", "1" or "other").
     */
    private String getConditionCode(boolean hasStart, boolean hasEnd) {
        var hasBoth = GenericOperations.allMatchLazy(
            () -> hasStart,
            () -> hasEnd
        );

        if (hasBoth) {
            return "0";
        }

        return GenericOperations.conditionalSelect(
            hasStart, 
            () -> "1", 
            () -> "other"
        );
    }

    /**
     * Check if data has a "start" period
     *
     * @param data the data object to check
     * @return True if it is the case, otherwise false
     */
    boolean hasStartPeriod(D data);

    /**
     * Check if data has an "end" period
     *
     * @param data the data object to check
     * @return True if it is the case, otherwise false
     */
    boolean hasEndPeriod(D data);

    /**
     * Format start period to a human-readable string
     *
     * @param data the data field to be converted
     * @return the formatted start period as a string (e.g., "from May 23, 2011")
     */
    String formatStartPeriod(D data);

    /**
     * Format end period to a human-readable string
     *
     * @param data the data field to be converted
     * @return the formatted end period as a string (e.g., "to Feb 7, 2015")
     */
    String formatEndPeriod(D data);
}
