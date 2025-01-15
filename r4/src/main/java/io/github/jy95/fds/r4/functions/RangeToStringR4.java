package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Range;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for converting range objects to human-readable strings.
 *
 * @author jy95
 */
public class RangeToStringR4 implements RangeToString<FDSConfigR4, Range> {

    private final QuantityToStringR4 quantityToStringR4;

    /**
     * Constructor for {@code RangeToStringR4}.
     */
    public RangeToStringR4() {
        quantityToStringR4 = new QuantityToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (hasHigh(range) && quantityToStringR4.hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return hasLow(range) && quantityToStringR4.hasUnit(range.getLow());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> getUnitText(ResourceBundle bundle, FDSConfigR4 config, Range range, boolean hasLow, boolean hasHigh) {
        return (hasHigh)
                ? quantityToStringR4.enhancedFromUnitToString(bundle, config, range.getHigh())
                : quantityToStringR4.enhancedFromUnitToString(bundle, config, range.getLow());
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
