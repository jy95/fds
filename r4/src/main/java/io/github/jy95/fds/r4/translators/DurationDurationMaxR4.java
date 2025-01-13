package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractDurationDurationMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.duration" / "timing.repeat.durationMax"
 *
 * @author jy95
 */
public class DurationDurationMaxR4 extends AbstractDurationDurationMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code DurationDurationMaxR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DurationDurationMaxR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasDuration(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDuration();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasDurationMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDurationMax();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasDurationUnit()
                && (hasDuration(dosage) || hasDurationMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected String turnDurationToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDuration();

        var durationText = quantityToString(durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    protected String turnDurationMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var durationText = quantityToString(durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
