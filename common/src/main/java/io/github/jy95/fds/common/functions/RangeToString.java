package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.operations.QuantityProcessor;

import java.math.BigDecimal;
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
     * @param translationService The service providing localized strings and configuration context.
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
     * Retrieves the utility class for processing Quantity within the ratio object
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
        
        // Check high first, more likely to be found in it
        var hasUnitHigh = hasHigh(range) && solver.hasUnit(getHigh(range));
        if (hasUnitHigh) {
            return true;
        }

        // Otherwise check low
        return hasLow(range) && solver.hasUnit(getLow(range));
    }

    /**
     * Convert a range without a unit to a human-readable string.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param range              The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithoutUnit(TranslationService<C> translationService, R range) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasLow = hasLow(range);
            boolean hasHigh = hasHigh(range);
            
            // Retrieve message format using the service
            var messageFormat = translationService.getMessage("amount.range.withoutUnit");

            String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

            // Note: getValue methods are expected to return BigDecimals which MessageFormat handles
            Map<String, Object> arguments = Map.of(
                    "minValue", hasLow ? getLowValue(range) : "",
                    "maxValue", hasHigh ? getHighValue(range) : "",
                    "condition", condition
            );

            return messageFormat.format(arguments);
        });
    }

    /**
     * Convert a range with a unit to a human-readable string.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param range              The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithUnit(TranslationService<C> translationService, R range) {
        boolean hasLow = hasLow(range);
        boolean hasHigh = hasHigh(range);
        
        // Retrieve message format using the service
        var messageFormat = translationService.getMessage("amount.range.withUnit");
        
        // Retrieve unit text, passing the service
        CompletableFuture<String> unitRetrieval = getUnitText(translationService, range, hasLow, hasHigh);

        String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {

                    Map<String, Object> arguments = Map.of(
                            "minValue", hasLow ? getLowValue(range) : "",
                            "maxValue", hasHigh ? getHighValue(range) : "",
                            "condition", condition,
                            "unit", unitAsText
                    );
                    return messageFormat.format(arguments);
                    
                });
    }

    /**
     * Retrieves the unit as text (either code or text).
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param range              The range object.
     * @param hasLow             Boolean indicating if a low value is present.
     * @param hasHigh            Boolean indicating if high value is present.
     * @return A CompletableFuture that resolves to the unit string.
     */
    default CompletableFuture<String> getUnitText(TranslationService<C> translationService, R range, boolean hasLow, boolean hasHigh) {
        var solver = getQuantityToString();
        var quantity = (hasHigh) ? getHigh(range) : getLow(range);
        return solver.enhancedFromUnitToString(translationService,quantity);
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

    /**
     * Retrieves the low value of the range.
     *
     * @param range The range object.
     * @return The low value.
     */
    BigDecimal getLowValue(R range);

    /**
     * Retrieves the high value of the range.
     *
     * @param range The range object.
     * @return The high value.
     */
    BigDecimal getHighValue(R range);
}