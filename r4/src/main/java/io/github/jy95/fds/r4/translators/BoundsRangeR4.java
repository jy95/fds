package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractBoundsRange;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsRange"
 *
 * @author jy95
 */
public class BoundsRangeR4 extends AbstractBoundsRange<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code BoundsRangeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public BoundsRangeR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();
        var bundle = this.getResources();

        return RangeToStringR4
                .getInstance()
                .convert(bundle, this.getConfig(), boundsRange)
                .thenApplyAsync(v -> boundsRangeMsg.format(new Object[]{v}));
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
