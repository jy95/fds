package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractBoundsRange;
import io.github.jy95.r4.config.FDSConfigR4;
import io.github.jy95.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class BoundsRangeR4 extends AbstractBoundsRange<FDSConfigR4, Dosage> {

    private final RangeToStringR4 rangeToStringR4;

    public BoundsRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();
        var bundle = this.getResources();

        return rangeToStringR4
                .convert(bundle, this.getConfig(), boundsRange)
                .thenApplyAsync(v -> boundsRangeMsg.format(new Object[]{v}));
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
