package jy95.fhir.r4.dosage.utils.functions;

import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Range;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class RangeToString {

    final static String DURATION_SYSTEM = "http://hl7.org/fhir/ValueSet/duration-units";

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
        if (range.hasHigh() && hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return range.hasLow() && hasUnit(range.getLow());
    }

    // See if unit (code or text) could be found in quantity
    private static boolean hasUnit(Quantity quantity) {
        return quantity.hasUnit() || quantity.hasCode();
    }

    // Convert a range without unit
    private static CompletableFuture<String> convertWithoutUnit(ResourceBundle bundle, FDUConfig config, Range range) {
        return CompletableFuture.supplyAsync(() -> {
            boolean hasLow = range.hasLow();
            boolean hasHigh = range.hasHigh();
            String msg = bundle.getString("amount.range.withoutUnit");

            // Both are present
            if (hasLow && hasHigh) {
                return MessageFormat.format(
                        msg,
                        0,
                        range.getLow().getValue().toString(),
                        range.getHigh().getValue().toString()
                );
            }

            // Only high is present
            if (hasHigh) {
                return MessageFormat.format(
                        msg,
                        1,
                        range.getHigh().getValue().toString()
                );
            }

            // Only low is present
            return MessageFormat.format(
                    msg,
                    2,
                    range.getLow().getValue().toString()
            );
        });
    }

    // Convert a range with unit
    private static CompletableFuture<String> convertWithUnit(ResourceBundle bundle, FDUConfig config, Range range) {
        boolean hasLow = range.hasLow();
        boolean hasHigh = range.hasHigh();
        String msg = bundle.getString("amount.range.withUnit");
        var unitRetrieval = (hasHigh)
                ? enhancedFromFHIRQuantityUnitToString(bundle, config, range.getHigh())
                : enhancedFromFHIRQuantityUnitToString(bundle, config, range.getLow());

        return unitRetrieval
                .thenApplyAsync(unitAsText -> {

                    // Both are present
                    if (hasHigh && hasLow) {
                        return MessageFormat.format(
                                msg,
                                0,
                                range.getLow().getValue(),
                                range.getHigh().getValue(),
                                unitAsText
                        );
                    }

                    // Only high is present
                    if (hasHigh) {
                        return MessageFormat.format(
                                msg,
                                1,
                                range.getHigh().getValue(),
                                unitAsText
                        );
                    }

                    // Only low is present
                    return MessageFormat.format(
                            msg,
                            2,
                            range.getLow().getValue(),
                            unitAsText
                    );

                });
    }

    // Enhanced version FromFHIRQuantityUnitToString, to deal with unitsOfTime directly
    private static CompletableFuture<String> enhancedFromFHIRQuantityUnitToString(ResourceBundle bundle, FDUConfig config, Quantity quantity) {

        // duration units are built-in supported
        if (quantity.hasSystem() && quantity.hasCode() && quantity.getSystem().equals(DURATION_SYSTEM)) {
            return CompletableFuture.supplyAsync(() -> {
                String code = quantity.getCode();
                BigDecimal amount = quantity.hasValue() ? quantity.getValue() : BigDecimal.ONE;
                String message = bundle.getString("withoutCount." + code);
                return MessageFormat.format(message,message, amount);
            });
        }

        // Otherwise let config do the charm
        return config.getFromFHIRQuantityUnitToString().apply(quantity);
    }
}
