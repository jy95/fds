package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.operations.QuantityProcessor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.instance.model.api.IBase;

/**
 * Interface for converting range objects to human-readable strings.
 *
 * @param <R> The type of range object to be converted.
 * @param <Q> The type of quantity object to be converted.
 * @param <C> The type of configuration object extending FDSConfig.
 * @author jy95
 * @since 1.0.0
 */
public interface RangeToString<R, Q extends IBase, C extends FDSConfig & QuantityProcessor<Q>> {

    /**
     * Converts a range object to a human-readable string asynchronously.
     *
     * @param translationService The service providing localized strings and
     *                           configuration context.
     * @param range              The range object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(TranslationService<C> translationService, R range) {
        if (hasUnit(range)) {
            return convertWithUnit(translationService, range);
        }
        return convertWithoutUnit(translationService, range);
    }

    /**
     * Retrieves the utility class for processing Quantity within the range object
     * 
     * @return a QuantityToString bound to the FHIR version
     */
    QuantityToString<Q, C> getQuantityToString();

    /**
     * Retrieve the high Quantity
     * 
     * @param range The range object.
     * @return The "high" quantity of the range
     */
    Q getHigh(R range);

    /**
     * Retrieve the low Quantity
     * 
     * @param range The range object.
     * @return The "low" quantity of the range
     */
    Q getLow(R range);

    /**
     * Determines if a range has a unit (either code or text).
     *
     * @param range The range object.
     * @return True if the range has a unit, false otherwise.
     */
    default boolean hasUnit(R range) {
        var solver = getQuantityToString();

        var hasHighUnit = GenericOperations.conditionalSelect(
            hasHigh(range), 
            () -> solver.hasUnit(getHigh(range)), 
            () -> false
        );

        var hasLowUnit = GenericOperations.conditionalSelect(
            hasLow(range), 
            () -> solver.hasUnit(getLow(range)), 
            () -> false
        );

        return GenericOperations.anyMatchLazy(
            () -> hasHighUnit,
            () -> hasLowUnit
        );
    }

    /**
     * Compute the base arguments required for formatting the range object.
     * 
     * @param range      The range object.
     * @param unitAsText The unit, if present
     * @return A Map containing "minValue", "maxValue", and "condition" at least
     */
    default Map<String, Object> getBaseArguments(R range, String unitAsText) {
        var hasLow = hasLow(range);
        var hasHigh = hasHigh(range);
        var solver = getQuantityToString();

        var condition = getConditionCode(hasLow, hasHigh);

        var minValue = hasLow ? solver.getValue(getLow(range)) : "";
        var maxValue = hasHigh ? solver.getValue(getHigh(range)) : "";

        return Map.of(
                "minValue", minValue,
                "maxValue", maxValue,
                "condition", condition,
                "unit", unitAsText);
    }

    /**
     * Determines the formatting condition code for the range.
     * 
     * @param hasLow  True if the range has a low bound.
     * @param hasHigh True if the range has a high bound.
     * @return The conditional code ("0", "1" or "2").
     */
    private String getConditionCode(boolean hasLow, boolean hasHigh) {
        // Full range [min - max]
        var hasBoth = GenericOperations.allMatchLazy(
            () -> hasLow,
            () -> hasHigh
        );

        if (hasBoth) {
            return "0";
        }

        // Max only [ - max]
        if (hasHigh) {
            return "1";
        }

        // Min only [min - ]
        return "2";
    }

    /**
     * Convert a range without a unit to a human-readable string.
     *
     * @param translationService The service providing localized strings and
     *                           configuration context.
     * @param range              The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithoutUnit(TranslationService<C> translationService, R range) {
        return CompletableFuture.supplyAsync(() -> {
            var arguments = getBaseArguments(range, "");
            var messageFormat = translationService.getMessage("amount.range.withoutUnit");
            return messageFormat.format(arguments);
        });
    }

    /**
     * Convert a range with a unit to a human-readable string.
     *
     * @param translationService The service providing localized strings and
     *                           configuration context.
     * @param range              The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithUnit(TranslationService<C> translationService, R range) {

        var messageFormat = translationService.getMessage("amount.range.withUnit");
        var unitRetrieval = getUnitText(translationService, range);

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {

                    var arguments = getBaseArguments(range, unitAsText);
                    return messageFormat.format(arguments);

                });
    }

    /**
     * Retrieves the unit as text (either code or text).
     *
     * @param translationService The service providing localized strings and
     *                           configuration context.
     * @param range              The range object.
     * @return A CompletableFuture that resolves to the unit string.
     */
    default CompletableFuture<String> getUnitText(TranslationService<C> translationService, R range) {
        var solver = getQuantityToString();
        var quantity = hasHigh(range) ? getHigh(range) : getLow(range);
        return solver.enhancedFromUnitToString(translationService, quantity);
    }

    /**
     * Checks if the range has a low value.
     *
     * @param range The range object.
     * @return True if the range has a low value, false otherwise.
     */
    boolean hasLow(R range);

    /**
     * Checks if the range has a high value.
     *
     * @param range The range object.
     * @return True if the range has a high value, false otherwise.
     */
    boolean hasHigh(R range);
}