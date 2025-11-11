package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for converting range objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <R> The type of range object to be converted.
 * @author jy95
 * @since 1.0.0
 */
public interface RangeToString<C extends FDSConfig, R> {

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
     * Determines if a range has a unit (either code or text).
     *
     * @param range The range object.
     * @return True if the range has a unit, false otherwise.
     */
    boolean hasUnit(R range);

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
                    // Use the non-shared MessageFormat instance retrieved earlier
                    Map<String, Object> arguments = Map.of(
                            "minValue", hasLow ? getLowValue(range) : "",
                            "maxValue", hasHigh ? getHighValue(range) : "",
                            "condition", condition,
                            "unit", unitAsText
                    );

                    // Note: The format method on MessageFormat is not thread-safe, 
                    // but translationService.getMessage(key) returns a new instance, 
                    // and this block is executed inside a .thenApplyAsync, 
                    // so it is confined to a thread, which makes it safe.
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
    CompletableFuture<String> getUnitText(TranslationService<C> translationService, R range, boolean hasLow, boolean hasHigh);

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