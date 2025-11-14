package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.Quantity;

import java.math.BigDecimal;

/**
 * R4 enum for converting quantity objects to human-readable strings.
 *
 * @author jy95
 */
public enum QuantityToStringR4 implements QuantityToString<Quantity, FDSConfigR4> {

    // Singleton instance
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
    public String getComparatorCode(Quantity quantity) {
        return quantity.getComparator().toCode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasSystem(Quantity quantity) {
        return quantity.hasSystem();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCode(Quantity quantity) {
        return quantity.hasCode();
    }

    /** {@inheritDoc} */
    @Override
    public String getSystem(Quantity quantity) {
        return quantity.getSystem();
    }

    /** {@inheritDoc} */
    @Override
    public String getCode(Quantity quantity) {
        return quantity.getCode();
    }
    
}
