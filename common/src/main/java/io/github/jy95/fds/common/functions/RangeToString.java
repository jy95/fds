package io.github.jy95.fds.common.functions;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;

import java.math.BigDecimal;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for converting range objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <R> The type of range object to be converted.
 * @author jy95
 */
public interface RangeToString<C extends FDSConfig, R> {

    /**
     * Converts a range object to a human-readable string asynchronously.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param range   The range object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(ResourceBundle bundle, C config, R range) {
        if (hasUnit(range)) {
            return convertWithUnit(bundle, config, range);
        }
        return convertWithoutUnit(bundle, config, range);
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
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param range   The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithoutUnit(ResourceBundle bundle, C config, R range) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasLow = hasLow(range);
            boolean hasHigh = hasHigh(range);
            String msg = bundle.getString("amount.range.withoutUnit");

            MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());

            String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

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
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param range   The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convertWithUnit(ResourceBundle bundle, C config, R range) {
        boolean hasLow = hasLow(range);
        boolean hasHigh = hasHigh(range);
        String msg = bundle.getString("amount.range.withUnit");
        CompletableFuture<String> unitRetrieval = getUnitText(bundle, config, range, hasLow, hasHigh);

        String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {
                    Map<String, Object> arguments = Map.of(
                            "minValue", hasLow ? getLowValue(range) : "",
                            "maxValue", hasHigh ? getHighValue(range) : "",
                            "condition", condition,
                            "unit", unitAsText
                    );

                    MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());
                    return messageFormat.format(arguments);
                });
    }

    /**
     * Retrieves the unit as text (either code or text).
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param range    The range object.
     * @param hasLow   Boolean indicating if low value is present.
     * @param hasHigh  Boolean indicating if high value is present.
     * @return A CompletableFuture that resolves to the unit string.
     */
    CompletableFuture<String> getUnitText(ResourceBundle bundle, C config, R range, boolean hasLow, boolean hasHigh);

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
