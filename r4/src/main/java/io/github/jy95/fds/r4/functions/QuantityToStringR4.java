package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.Quantity;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * R4 enum for converting quantity objects to human-readable strings.
 *
 * @author jy95
 */
public enum QuantityToStringR4 implements QuantityToString<FDSConfigR4, Quantity> {

    INSTANCE;

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
    public boolean hasComparator(Quantity quantity) {
        return quantity.hasComparator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> enhancedFromUnitToString(TranslationService<FDSConfigR4> translationService, Quantity quantity) {
        // Duration units are built-in supported
        var config = translationService.getConfig();
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
    public String getComparatorCode(Quantity quantity) {
        return quantity.getComparator().toCode();
    }
    
}
