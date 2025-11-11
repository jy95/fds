package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Quantity;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for converting quantity objects to human-readable strings.
 * Implements the Bill Pugh Singleton pattern for thread-safe lazy initialization.
 *
 * @author jy95
 */
public class QuantityToStringR5 implements QuantityToString<FDSConfigR5, Quantity> {

    // Private constructor to prevent instantiation
    private QuantityToStringR5() {}

    // Static inner class for holding the singleton instance
    private static class Holder {
        private static final QuantityToStringR5 INSTANCE = new QuantityToStringR5();
    }

    /**
     * Returns the singleton instance of QuantityToStringR5.
     *
     * @return the singleton instance
     */
    public static QuantityToStringR5 getInstance() {
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
    public CompletableFuture<String> enhancedFromUnitToString(TranslationService<FDSConfigR5> translationService, Quantity quantity) {
        var config = translationService.getConfig();

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
    public boolean hasComparator(Quantity quantity) {
        return quantity.hasComparator();
    }

    /** {@inheritDoc} */
    @Override
    public String getComparatorCode(Quantity quantity) {
        return quantity.getComparator().toCode();
    }
}
