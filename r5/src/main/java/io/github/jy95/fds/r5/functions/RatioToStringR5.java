package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.r5.config.FDSConfigR5;

import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Ratio;

/**
 * R5 enum for converting ratio objects to human-readable strings.
 *
 * @author jy95
 */
public enum RatioToStringR5 implements RatioToString<Ratio, Quantity, FDSConfigR5> {

    // Singleton
    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasNumerator(Ratio ratio) {
        return ratio.hasNumerator();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDenominator(Ratio ratio) {
        return ratio.hasDenominator();
    }

    /** {@inheritDoc} */
    @Override
    public QuantityToString<Quantity, FDSConfigR5> getQuantityToString() {
        return QuantityToStringR5.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getNumerator(Ratio ratio) {
        return ratio.getNumerator();
    }

    /** {@inheritDoc} */
    @Override
    public Quantity getDenominator(Ratio ratio) {
        return ratio.getDenominator();
    }

}