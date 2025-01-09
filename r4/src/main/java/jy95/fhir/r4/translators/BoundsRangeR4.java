package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractBoundsRange;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class BoundsRangeR4 extends AbstractBoundsRange<FDSConfigR4, Dosage> {

    private final RangeToStringR4 rangeToStringR4;

    public BoundsRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
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
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
