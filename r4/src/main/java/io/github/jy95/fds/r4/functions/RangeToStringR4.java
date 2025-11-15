package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Range;

/**
 * R4 enum for converting range objects to human-readable strings.
 *
 * @author jy95
 */
public enum RangeToStringR4 implements RangeToString<Range, Quantity, FDSConfigR4> {

    // Singleton
    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasLow(Range range) {
        return range.hasLow();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasHigh(Range range) {
        return range.hasHigh();
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getHigh(Range range) {
        return range.getHigh();
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getLow(Range range) {
        return range.getLow();
    }

    @Override
    public QuantityToString<Quantity, FDSConfigR4> getQuantityToString() {
        return QuantityToStringR4.INSTANCE;
    }
}
