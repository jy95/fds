package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for converting quantity objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <Q> The type of quantity object to be converted.
 * @author jy95
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
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param quantity The quantity object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(ResourceBundle bundle, C config, Q quantity) {
        var comparator = comparatorToString(bundle, config, quantity);
        var unit = hasUnit(quantity)
                ? enhancedFromUnitToString(config, quantity)
                : CompletableFuture.completedFuture("");
        var amount = getValue(quantity).toString();

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
     * Provides enhanced logic for converting units to a human-readable string.
     *
     * @param config   The configuration object for additional logic.
     * @param quantity The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the unit.
     */
    CompletableFuture<String> enhancedFromUnitToString(C config, Q quantity);

    /**
     * Converts the comparator of a quantity to a human-readable string.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param quantity The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the comparator.
     */
    CompletableFuture<String> comparatorToString(ResourceBundle bundle, C config, Q quantity);
}
