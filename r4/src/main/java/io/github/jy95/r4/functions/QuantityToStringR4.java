package io.github.jy95.r4.functions;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.r4.model.Quantity;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.common.functions.AbstractQuantityToString;
import io.github.jy95.r4.config.FDSConfigR4;

public class QuantityToStringR4 extends AbstractQuantityToString<FDSConfigR4, Quantity> {

    @Override
    public boolean hasUnit(Quantity quantity) {
        return quantity.hasUnit() || quantity.hasCode();
    }

    @Override
    public BigDecimal getValue(Quantity quantity) {
        return quantity.getValue();
    }

    @Override
    public CompletableFuture<String> enhancedFromUnitToString(ResourceBundle bundle, FDSConfigR4 config, Quantity quantity) {

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

    @Override
    public CompletableFuture<String> comparatorToString(ResourceBundle bundle, FDSConfigR4 config, Quantity quantity) {
        if (quantity.hasComparator()) {
            var code = quantity.getComparator().toCode();
            var comparatorMsg = bundle.getString(code);
            var text = new MessageFormat(comparatorMsg, config.getLocale()).format(new Object[]{});
            return CompletableFuture.completedFuture(text);
        }
        return CompletableFuture.completedFuture("");
    }
    
}
