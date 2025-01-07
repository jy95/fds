package jy95.fhir.common.functions;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;

public abstract class AbstractRangeToString<C extends FDSConfig, R> {

    /**
     * Converts a range object to a human-readable string asynchronously.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param range   The range object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    public CompletableFuture<String> convert(ResourceBundle bundle, C config, R range) {
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
    protected abstract boolean hasUnit(R range);

    /**
     * Convert a range without a unit to a human-readable string.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param range   The range object.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    protected CompletableFuture<String> convertWithoutUnit(ResourceBundle bundle, C config, R range) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasLow = hasLow(range);
            boolean hasHigh = hasHigh(range);
            String msg = bundle.getString("amount.range.withoutUnit");

            // Use ICU MessageFormat for formatting
            MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());

            // Determine the condition based on the range's presence of low and high values
            String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

            // Create a map for the named arguments
            Map<String, Object> arguments = Map.of(
                    "minValue", hasLow ? getLowValue(range) : "",
                    "maxValue", hasHigh ? getHighValue(range) : "",
                    "condition", condition
            );

            // Format and return the result
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
    protected CompletableFuture<String> convertWithUnit(ResourceBundle bundle, C config, R range) {
        boolean hasLow = hasLow(range);
        boolean hasHigh = hasHigh(range);
        String msg = bundle.getString("amount.range.withUnit");
        CompletableFuture<String> unitRetrieval = getUnitText(bundle, config, range, hasLow, hasHigh);

        // Determine the condition based on the range's presence of low and high values
        String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {
                    // Create a map for the named arguments
                    Map<String, Object> arguments = Map.of(
                            "minValue", hasLow ? getLowValue(range) : "",
                            "maxValue", hasHigh ? getHighValue(range) : "",
                            "condition", condition,
                            "unit", unitAsText
                    );

                    // Format the message
                    MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());
                    return messageFormat.format(arguments);
                });
    }

    /**
     * Abstract method to retrieve the unit as text (either code or text).
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param range    The range object.
     * @param hasLow   Boolean indicating if low value is present.
     * @param hasHigh  Boolean indicating if high value is present.
     * @return A CompletableFuture that resolves to the unit string.
     */
    protected abstract CompletableFuture<String> getUnitText(ResourceBundle bundle, C config, R range, boolean hasLow, boolean hasHigh);

    /**
     * Abstract method to check if the range has a low value.
     *
     * @param range The range object.
     * @return True if the range has a low value, false otherwise.
     */
    protected abstract boolean hasLow(R range);

    /**
     * Abstract method to check if the range has a high value.
     *
     * @param range The range object.
     * @return True if the range has a high value, false otherwise.
     */
    protected abstract boolean hasHigh(R range);

    /**
     * Abstract method to retrieve the low value of the range.
     *
     * @param range The range object.
     * @return The low value.
     */
    protected abstract String getLowValue(R range);

    /**
     * Abstract method to retrieve the high value of the range.
     *
     * @param range The range object.
     * @return The high value.
     */
    protected abstract String getHighValue(R range);
}
