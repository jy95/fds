package io.github.jy95.fds.common.functions;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.instance.model.api.IBase;
import org.hl7.fhir.instance.model.api.IPrimitiveType;

import io.github.jy95.fds.common.config.FDSConfig;

/**
 * Interface for converting a period to a human-readable string.
 *
 * @param <P> The type of the period object.
 * @param <D> The type of the date objects within the period.
 * @param <C> The type of the configuration used for translation.
 * @author jy95
 * @since 2.1.9
 */
public interface PeriodToString<P extends IBase, D extends IPrimitiveType<Date>, C extends FDSConfig> {
    
    /* True if the period has a start date */
    boolean hasStart(P period);
    /* True if the period has an end date */
    boolean hasEnd(P period);
    /* Get the start date of the period */
    D getStart(P period);
    /* Get the end date of the period */
    D getEnd(P period);
    /* Format a date (start or end) of the period */
    String formatDateTime(TranslationService<C> translationService, D date);

    /**
     * Determines the formatting condition code for the period.
     * 
     * @param hasStart  True if the period has a start date.
     * @param hasEnd True if the period has an end date.
     * @return The conditional code ("0", "1" or "other").
     */
    default String getConditionCode(boolean hasStart, boolean hasEnd) {
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
     * Extract information about the boundsPeriod
     *
     * @param translationService The service providing localized string translations.
     * @param data The data object to analyze
     * @return A map
     */
    default Map<String, Object> extractInformation(TranslationService<C> translationService, P data) {
        // Check conditions
        var hasStart = hasStart(data);
        var hasEnd = hasEnd(data);

        // Prepare date values using FormatDateTimes.convert()
        String startDate = GenericOperations.conditionalSelect(
            hasStart, 
            () -> formatDateTime(translationService, getStart(data)), 
            () -> ""
        );

        String endDate = GenericOperations.conditionalSelect(
            hasEnd, 
            () -> formatDateTime(translationService, getEnd(data)), 
            () -> ""
        );

        // Choose the correct condition based on the presence of start and end dates
        String condition = getConditionCode(hasStart, hasEnd);

        // Create a map of named arguments
        return Map.of(
            "startDate", startDate,
            "endDate", endDate,
            "condition", condition
        );
    }

    /**
     * Provides enhanced logic for converting a period to a human-readable string.
     *
     * @param translationService The service providing localized string
     *                           translations.
     * @param period            The period object to convert.
     * @return A CompletableFuture that resolves to the human-readable string for
     *         the period.
     */
    default CompletableFuture<String> convert(TranslationService<C> translationService, P period) {
        return CompletableFuture.supplyAsync(() -> {
            var arguments = extractInformation(translationService, period);
            // Format the message with the named arguments
            var boundsPeriodMsg = translationService.getMessage("fields.boundsPeriod");
            return boundsPeriodMsg.format(arguments);
        });
    }

}
