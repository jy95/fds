package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.RangeToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Range;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * R5 enum for converting range objects to human-readable strings.
 *
 * @author jy95
 */
public enum RangeToStringR5 implements RangeToString<FDSConfigR5, Range> {

    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasUnit(Range range) {
        // Check high first, more likely to be found in it
        if (hasHigh(range) && QuantityToStringR5.INSTANCE.hasUnit(range.getHigh())) {
            return true;
        }
        // Otherwise check low
        return hasLow(range) && QuantityToStringR5.INSTANCE.hasUnit(range.getLow());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> getUnitText(TranslationService<FDSConfigR5> translationService, Range range, boolean hasLow, boolean hasHigh) {
        return QuantityToStringR5
                .INSTANCE
                .enhancedFromUnitToString(
                        translationService,
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
