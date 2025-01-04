package jy95.fhir.r4.dosage.utils.functions;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Quantity;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class QuantityToString {
    public static CompletableFuture<String> convert(ResourceBundle bundle, FDUConfig config, Quantity quantity) {
        var comparator = comparatorToString(bundle, config, quantity);
        var unit = unitToString(config, quantity);
        var amount = quantity.getValue().toString();

        return comparator.thenCombineAsync(unit, (comparatorText, unitText) ->
                Stream
                        .of(comparatorText, amount, unitText)
                        .filter(part -> !part.isEmpty())
                        .collect(Collectors.joining(" "))
        );
    }

    // See if unit (code or text) could be found in quantity
    private static boolean hasUnit(Quantity quantity) {
        return quantity.hasUnit() || quantity.hasCode();
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

    private static CompletableFuture<String> unitToString(FDUConfig config, Quantity quantity) {
        if (hasUnit(quantity)) {
            return config.getFromFHIRQuantityUnitToString().apply(quantity);
        }
        return CompletableFuture.completedFuture("");
    }
}
