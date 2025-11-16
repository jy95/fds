package io.github.jy95.fds.common.config;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Locale;
import java.util.List;

import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.jy95.fds.common.types.DisplayOrder;

/**
 * Configuration class for controlling how dosages are handled and displayed.
 *
 * @author jy95
 * @since 1.0.0
 */
@Getter
@SuperBuilder
public class FDSConfig {
    /**
     * Locale to return the humanized dosage string
     * Default: English
     */
    @Builder.Default
    private final Locale locale = Locale.ENGLISH;
    /**
     * Control the display order used by the algorithm
     * Useful when you want to turn on / off some specific rules for some reason
     */
    @Builder.Default
    private final List<DisplayOrder> displayOrder = DefaultImplementations.DEFAULT_DISPLAY_ORDER;
    /**
     * Override separator between each part of "Dosage"
     */
    @Builder.Default
    private final String displaySeparator = DefaultImplementations.DEFAULT_SEPARATOR;
    /**
     * Function to select the ResourceBundle of a given locale.
     * Useful in case you would like to override or support other locales that built
     * ones.
     * Please check the built-in implemented locales for the keys and messages
     * format.
     * Keep in mind that formats may change between versions, so be cautious of
     * compatibility.
     * Consider contributing improvements or additional locales as well.
     */
    @Builder.Default
    private final Function<Locale, ResourceBundle> selectResourceBundle = DefaultImplementations::selectResourceBundle;

    /**
     * Function to format quantity numbers for display.
     * Useful in case you would like to customize number formatting.
     * By default, it uses ICU4J-based formatting via DefaultImplementations.
     */
    @Builder.Default
    private final BiFunction<Locale, java.math.BigDecimal, String> formatQuantityNumber = DefaultImplementations::formatQuantityNumber;
}
