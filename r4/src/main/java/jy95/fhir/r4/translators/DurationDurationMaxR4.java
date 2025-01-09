package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractDurationDurationMax;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

public class DurationDurationMaxR4 extends AbstractDurationDurationMax<FDSConfigR4, Dosage> {

    public DurationDurationMaxR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasDurationUnit()
                && (hasDuration(dosage) || hasDurationMax(dosage));
    }

    @Override
    protected boolean hasDuration(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDuration();
    }

    @Override
    protected boolean hasDurationMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDurationMax();
    }

    @Override
    protected String turnDurationToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDuration();

        var durationText = quantityToString(durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    @Override
    protected String turnDurationMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var durationText = quantityToString(durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
