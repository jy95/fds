package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Range;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for converting range objects to human-readable strings.
 * Implements the Bill Pugh Singleton pattern for thread-safe lazy initialization.
 *
 * @author jy95
 */
public class RangeToStringR5 implements RangeToString<FDSConfigR5, Range> {

    // Private constructor to prevent instantiation
    private RangeToStringR5() {}

    // Static inner class for holding the singleton instance
    private static class Holder {
        private static final RangeToStringR5 INSTANCE = new RangeToStringR5();
    }

    /**
     * Returns the singleton instance of RangeToStringR5.
     *
     * @return the singleton instance
     */
    public static RangeToStringR5 getInstance() {
        return Holder.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (hasHigh(range) && QuantityToStringR5.getInstance().hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return hasLow(range) && QuantityToStringR5.getInstance().hasUnit(range.getLow());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> getUnitText(ResourceBundle bundle, FDSConfigR5 config, Range range, boolean hasLow, boolean hasHigh) {
        return QuantityToStringR5
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
