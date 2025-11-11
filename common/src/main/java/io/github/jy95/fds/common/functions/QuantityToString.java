package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for converting quantity objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <Q> The type of quantity object to be converted.
 * @author jy95
 * @since 1.0.0
 */
public interface QuantityToString<C extends FDSConfig, Q> {

    /**
     * System URI for duration units in FHIR.
     */
    String DURATION_SYSTEM = "http://hl7.org/fhir/ValueSet/duration-units";
    /**
     * System URI for units of time in FHIR.
     */
    String UNITS_OF_TIME_SYSTEM = "http://hl7.org/fhir/ValueSet/units-of-time";
    /**
     * List of time-related systems.
     */
    List<String> TIME_SYSTEMS = List.of(DURATION_SYSTEM, UNITS_OF_TIME_SYSTEM);

    /**
     * Converts a quantity object to a human-readable string asynchronously.
     *
     * @param translationService The service providing localized string translations.
     * @param quantity           The quantity object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(TranslationService<C> translationService, Q quantity) {
        var config = translationService.getConfig();
        var comparator = comparatorToString(translationService, quantity);
        var unit = hasUnit(quantity)
                ? enhancedFromUnitToString(translationService, quantity)
                : CompletableFuture.completedFuture("");
        
        var amount = config
            .getFormatQuantityNumber()
            .apply(
                config.getLocale(),
                getValue(quantity)
            );

        return comparator.thenCombineAsync(unit, (comparatorText, unitText) ->
                Stream
                        .of(comparatorText, amount, unitText)
                        .filter(part -> !part.isEmpty())
                        .collect(Collectors.joining(" "))
        );
    }

    /**
     * Determines if a quantity has a unit.
     *
     * @param quantity The quantity object.
     * @return True if the quantity has a unit, false otherwise.
     */
    boolean hasUnit(Q quantity);

    /**
     * Retrieves the numeric value of the quantity.
     *
     * @param quantity The quantity object.
     * @return The numeric value of the quantity.
     */
    BigDecimal getValue(Q quantity);

    /**
     * Checks if the quantity has a comparator.
     *
     * @param quantity The quantity object.
     * @return True if the quantity has a comparator, false otherwise.
     */
    boolean hasComparator(Q quantity);

    /**
     * Retrieves the comparator code of the quantity.
     *
     * @param quantity The quantity object.
     * @return The comparator code as a string.
     */
    String getComparatorCode(Q quantity);

    /**
     * Provides enhanced logic for converting units to a human-readable string.
     *
     * @param translationService The service providing localized string translations.
     * @param quantity The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the unit.
     */
    CompletableFuture<String> enhancedFromUnitToString(TranslationService<C> translationService, Q quantity);

    /**
     * Converts the comparator of a quantity to a human-readable string.
     *
     * @param translationService The service providing localized string translations.
     * @param quantity           The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the comparator.
     */
    default CompletableFuture<String> comparatorToString(TranslationService<C> translationService, Q quantity) {
        if (hasComparator(quantity)) {
            var code = getComparatorCode(quantity);
            var comparatorMsg = translationService.getText(code);
            return CompletableFuture.completedFuture(comparatorMsg);
        }
        return CompletableFuture.completedFuture("");
    }
}