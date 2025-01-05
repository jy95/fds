package jy95.fhir.r4.dosage.utils.functions;

import com.ibm.icu.text.MessageFormat;

import org.hl7.fhir.r4.model.Range;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public final class RangeToString {

    public static CompletableFuture<String> convert(ResourceBundle bundle, FDUConfig config, Range range) {
        boolean hasUnitInRange = hasUnit(range);
        if (hasUnitInRange) {
            return convertWithUnit(bundle, config, range);
        }
        return convertWithoutUnit(bundle, config, range);
    }

    // See if unit (code or text) could be found in range
    private static boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (range.hasHigh() && QuantityToString.hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return range.hasLow() && QuantityToString.hasUnit(range.getLow());
    }

    // Convert a range without unit
    private static CompletableFuture<String> convertWithoutUnit(ResourceBundle bundle, FDUConfig config, Range range) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasLow = range.hasLow();
            boolean hasHigh = range.hasHigh();
            String msg = bundle.getString("amount.range.withoutUnit");

            // Use ICU MessageFormat for formatting
            MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());

            // Determine the condition
            String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

            // Create a map for the named arguments
            Map<String, Object> arguments = Map.of(
                    "minValue", hasLow ? range.getLow().getValue().toString() : "",
                    "maxValue", hasHigh ? range.getHigh().getValue().toString() : "",
                    "condition", condition
            );

            // Format and return the result
            return messageFormat.format(arguments);
        });
    }

    // Convert a range with unit
    private static CompletableFuture<String> convertWithUnit(ResourceBundle bundle, FDUConfig config, Range range) {
        boolean hasLow = range.hasLow();
        boolean hasHigh = range.hasHigh();
        String msg = bundle.getString("amount.range.withUnit");
        var unitRetrieval = (hasHigh)
                ? QuantityToString.enhancedFromFHIRQuantityUnitToString(bundle, config, range.getHigh())
                : QuantityToString.enhancedFromFHIRQuantityUnitToString(bundle, config, range.getLow());

        // Determine the condition
        String condition = (hasLow && hasHigh) ? "0" : (hasHigh) ? "1" : (hasLow) ? "2" : "other";

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {

                    // Create a map for the named arguments
                    Map<String, Object> arguments = Map.of(
                            "minValue", hasLow ? range.getLow().getValue() : "",
                            "maxValue", hasHigh ? range.getHigh().getValue() : "",
                            "condition", condition,
                            "unit", unitAsText
                    );

                    // Format the message
                    MessageFormat messageFormat = new MessageFormat(msg, config.getLocale());
                    return messageFormat.format(arguments);
                });
    }

}
