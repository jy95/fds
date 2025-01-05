package jy95.fhir.r4.dosage.utils.functions;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Quantity;

import java.math.BigDecimal;
import com.ibm.icu.text.MessageFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QuantityToString {

    final static String DURATION_SYSTEM = "http://hl7.org/fhir/ValueSet/duration-units";
    final static String UNITS_OF_TIME_SYSTEM = "http://hl7.org/fhir/ValueSet/units-of-time";
    final static List<String> TIME_SYSTEMS = List.of(DURATION_SYSTEM, UNITS_OF_TIME_SYSTEM);

    public static CompletableFuture<String> convert(ResourceBundle bundle, FDUConfig config, Quantity quantity) {
        var comparator = comparatorToString(bundle, config, quantity);
        var unit = hasUnit(quantity)
                ? enhancedFromFHIRQuantityUnitToString(bundle, config, quantity)
                : CompletableFuture.completedFuture("");
        var amount = quantity.getValue().toString();

        return comparator.thenCombineAsync(unit, (comparatorText, unitText) ->
                Stream
                        .of(comparatorText, amount, unitText)
                        .filter(part -> !part.isEmpty())
                        .collect(Collectors.joining(" "))
        );
    }

    // See if unit (code or text) could be found in quantity
    public static boolean hasUnit(Quantity quantity) {
        return quantity.hasUnit() || quantity.hasCode();
    }

    // Enhanced version FromFHIRQuantityUnitToString, to deal with unitsOfTime directly
    public static CompletableFuture<String> enhancedFromFHIRQuantityUnitToString(ResourceBundle bundle, FDUConfig config, Quantity quantity) {

        // duration units are built-in supported
        if (quantity.hasSystem() && quantity.hasCode() && TIME_SYSTEMS.contains(quantity.getSystem())) {
            return CompletableFuture.supplyAsync(() -> {
                String code = quantity.getCode();
                BigDecimal amount = quantity.hasValue() ? quantity.getValue() : BigDecimal.ONE;
                String message = bundle.getString("withoutCount." + code);
                return MessageFormat.format(message, amount);
            });
        }

        // Otherwise let config do the charm
        return config.getFromFHIRQuantityUnitToString().apply(quantity);
    }

    private static CompletableFuture<String> comparatorToString(ResourceBundle bundle, FDUConfig config, Quantity quantity) {
        if (quantity.hasComparator()) {
            var code = quantity.getComparator().toCode();
            var comparatorMsg = bundle.getString(code);
            var text = new MessageFormat(comparatorMsg, config.getLocale()).format(new Object[]{});
            return CompletableFuture.completedFuture(text);
        }
        return CompletableFuture.completedFuture("");
    }
}