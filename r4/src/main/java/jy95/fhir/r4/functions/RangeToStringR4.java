package jy95.fhir.r4.functions;

import jy95.fhir.common.functions.AbstractRangeToString;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Range;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class RangeToStringR4 extends AbstractRangeToString<FDSConfigR4, Range> {

    private final QuantityToStringR4 quantityToStringR4;

    public RangeToStringR4() {
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    protected boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (hasHigh(range) && quantityToStringR4.hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return hasLow(range) && quantityToStringR4.hasUnit(range.getLow());
    }

    @Override
    protected CompletableFuture<String> getUnitText(ResourceBundle bundle, FDSConfigR4 config, Range range, boolean hasLow, boolean hasHigh) {
        return (hasHigh)
                ? quantityToStringR4.enhancedFromUnitToString(bundle, config, range.getHigh())
                : quantityToStringR4.enhancedFromUnitToString(bundle, config, range.getLow());
    }

    @Override
    protected boolean hasLow(Range range) {
        return range.hasLow();
    }

    @Override
    protected boolean hasHigh(Range range) {
        return range.hasHigh();
    }

    @Override
    protected BigDecimal getLowValue(Range range) {
        return range.getLow().getValue();
    }

    @Override
    protected BigDecimal getHighValue(Range range) {
        return range.getHigh().getValue();
    }
}
