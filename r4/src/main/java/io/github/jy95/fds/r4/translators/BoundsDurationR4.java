package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractBoundsDuration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
public class BoundsDurationR4 extends AbstractBoundsDuration<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    /**
     * Constructor for {@code BoundsDurationR4}.
     *
     * @param config The configuration object used for translation.
     */
    public BoundsDurationR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var boundsDuration = dosage.getTiming().getRepeat().getBoundsDuration();
        return quantityToStringR4
                .convert(bundle, getConfig(), boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsDuration();
    }
}
