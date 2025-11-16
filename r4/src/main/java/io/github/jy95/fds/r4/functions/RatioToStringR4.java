package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Ratio;

/**
 * R4 enum for converting ratio objects to human-readable strings.
 *
 * @author jy95
 */
public enum RatioToStringR4 implements RatioToString<Ratio, Quantity, FDSConfigR4> {

    /**
     * Singleton
     */
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
    public QuantityToString<Quantity, FDSConfigR4> getQuantityToString() {
        return QuantityToStringR4.INSTANCE;
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
