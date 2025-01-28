package io.github.jy95.fds.common.config;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Locale;
import java.util.List;

import java.util.ResourceBundle;
import java.util.function.Function;

import io.github.jy95.fds.common.types.DisplayOrder;

/**
 * Configuration class for controlling how dosages are handled and displayed.
 *
 * @author jy95
 */
@Getter
@SuperBuilder
public class FDSConfig {
    /**
     * Locale to return the humanized dosage string
     * Default: English
     */
    @Builder.Default private final Locale locale = Locale.ENGLISH;
    /**
     * Control the display order used by the algorithm
     * Useful when you want to turn on / off some specific rules for some reason
     */
    @Builder.Default private final List<DisplayOrder> displayOrder = List.of(
            DisplayOrder.METHOD,
            DisplayOrder.DOSE_QUANTITY,
            DisplayOrder.DOSE_RANGE,
            DisplayOrder.RATE_RATIO,
            DisplayOrder.RATE_QUANTITY,
            DisplayOrder.RATE_RANGE,
            DisplayOrder.DURATION_DURATION_MAX,
            DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
            DisplayOrder.OFFSET_WHEN,
            DisplayOrder.DAY_OF_WEEK,
            DisplayOrder.TIME_OF_DAY,
            DisplayOrder.ROUTE,
            DisplayOrder.SITE,
            DisplayOrder.AS_NEEDED,
            DisplayOrder.BOUNDS_DURATION,
            DisplayOrder.BOUNDS_PERIOD,
            DisplayOrder.BOUNDS_RANGE,
            DisplayOrder.COUNT_COUNT_MAX,
            DisplayOrder.TIMING_EVENT,
            DisplayOrder.TIMING_CODE,
            DisplayOrder.MAX_DOSE_PER_PERIOD,
            DisplayOrder.MAX_DOSE_PER_ADMINISTRATION,
            DisplayOrder.MAX_DOSE_PER_LIFETIME,
            DisplayOrder.ADDITIONAL_INSTRUCTION,
            DisplayOrder.PATIENT_INSTRUCTION
    );
    // Override separator between each part of "Dosage"
    @Builder.Default private final String displaySeparator = " - ";
    /**
     * Function to select the ResourceBundle of a given locale.
     * Useful in case you would like to override or support other locales that built ones.
     * Please check the built-in implemented locales for the keys and messages format.
     * Keep in mind that formats may change between versions, so be cautious of compatibility.
     * Consider contributing improvements or additional locales as well.
     */
    @Builder.Default private final Function<Locale, ResourceBundle> selectResourceBundle = DefaultImplementations::selectResourceBundle;
}
