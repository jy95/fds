package io.github.jy95.fds.r4.functions;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Quantity;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for converting quantity objects to human-readable strings.
 * Implements the Bill Pugh Singleton pattern for thread-safe lazy initialization.
 *
 * @author jy95
 */
public class QuantityToStringR4 implements QuantityToString<FDSConfigR4, Quantity> {

    // Private constructor to prevent instantiation
    private QuantityToStringR4() {}

    // Static inner class for holding the singleton instance
    private static class Holder {
        private static final QuantityToStringR4 INSTANCE = new QuantityToStringR4();
    }

    /**
     * Returns the singleton instance of QuantityToStringR4.
     *
     * @return the singleton instance
     */
    public static QuantityToStringR4 getInstance() {
        return Holder.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnit(Quantity quantity) {
        return quantity.hasUnit() || quantity.hasCode();
    }

    /** {@inheritDoc} */
    @Override
    public BigDecimal getValue(Quantity quantity) {
        return quantity.getValue();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> enhancedFromUnitToString(FDSConfigR4 config, Quantity quantity) {
        // Duration units are built-in supported
        if (quantity.hasSystem() && quantity.hasCode() && TIME_SYSTEMS.contains(quantity.getSystem())) {
            return CompletableFuture.supplyAsync(() -> {
                String code = quantity.getCode();
                BigDecimal amount = quantity.hasValue() ? quantity.getValue() : BigDecimal.ONE;
                return UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), code, amount);
            });
        }

        // Otherwise, let config do the charm
        return config.fromFHIRQuantityUnitToString(quantity);
    }

    /** {@inheritDoc} */
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
