package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.r5.config.FDSConfigR5;

import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Range;

/**
 * R5 enum for converting range objects to human-readable strings.
 *
 * @author jy95
 */
public enum RangeToStringR5 implements RangeToString<Range, Quantity, FDSConfigR5> {

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
    public QuantityToString<Quantity, FDSConfigR5> getQuantityToString() {
        return QuantityToStringR5.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getHigh(Range range) {
        return range.getLow();
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getLow(Range range) {
        return range.getLow();
    }
}
