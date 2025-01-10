package io.github.jy95.fds.common.functions;

import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.jy95.fds.common.config.FDSConfig;

/**
 * Abstract base class for converting quantity objects to human-readable strings.
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <Q> The type of quantity object to be converted.
 */
public abstract class AbstractQuantityToString<C extends FDSConfig, Q> {

    /**
     * System URI for duration units in FHIR.
     */
    final protected String DURATION_SYSTEM = "http://hl7.org/fhir/ValueSet/duration-units";
    /**
     * System URI for units of time in FHIR.
     */
    final protected String UNITS_OF_TIME_SYSTEM = "http://hl7.org/fhir/ValueSet/units-of-time";
    /**
     * List of time-related systems.
     */
    final protected List<String> TIME_SYSTEMS = List.of(DURATION_SYSTEM, UNITS_OF_TIME_SYSTEM);

    /**
     * Converts a quantity object to a human-readable string asynchronously.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param quantity The quantity object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    public CompletableFuture<String> convert(ResourceBundle bundle, C config, Q quantity) {
        var comparator = comparatorToString(bundle, config, quantity);
        var unit = hasUnit(quantity)
                ? enhancedFromUnitToString(bundle, config, quantity)
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
    public abstract boolean hasUnit(Q quantity);

    /**
     * Retrieves the numeric value of the quantity.
     *
     * @param quantity The quantity object.
     * @return The numeric value of the quantity.
     */
    public abstract BigDecimal getValue(Q quantity);

    /**
     * Provides enhanced logic for converting units to a human-readable string.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param quantity The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the unit.
     */
    public abstract CompletableFuture<String> enhancedFromUnitToString(ResourceBundle bundle, C config, Q quantity);

    /**
     * Converts the comparator of a quantity to a human-readable string.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param quantity The quantity object.
     * @return A CompletableFuture that resolves to the human-readable string for the comparator.
     */
    public abstract CompletableFuture<String> comparatorToString(ResourceBundle bundle, C config, Q quantity);
}
