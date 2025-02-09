package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Range;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for converting range objects to human-readable strings.
 * Implements the Bill Pugh Singleton pattern for thread-safe lazy initialization.
 *
 * @author jy95
 */
public class RangeToStringR4 implements RangeToString<FDSConfigR4, Range> {

    // Private constructor to prevent instantiation
    private RangeToStringR4() {}

    // Static inner class for holding the singleton instance
    private static class Holder {
        private static final RangeToStringR4 INSTANCE = new RangeToStringR4();
    }

    /**
     * Returns the singleton instance of RangeToStringR4.
     *
     * @return the singleton instance
     */
    public static RangeToStringR4 getInstance() {
        return Holder.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (hasHigh(range) && QuantityToStringR4.getInstance().hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return hasLow(range) && QuantityToStringR4.getInstance().hasUnit(range.getLow());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> getUnitText(ResourceBundle bundle, FDSConfigR4 config, Range range, boolean hasLow, boolean hasHigh) {
        return QuantityToStringR4
                .getInstance()
                .enhancedFromUnitToString(
                        config,
                        (hasHigh) ? range.getHigh() : range.getLow()
                );
    }

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
    public BigDecimal getLowValue(Range range) {
        return range.getLow().getValue();
    }

    /** {@inheritDoc} */
    @Override
    public BigDecimal getHighValue(Range range) {
        return range.getHigh().getValue();
    }
}
