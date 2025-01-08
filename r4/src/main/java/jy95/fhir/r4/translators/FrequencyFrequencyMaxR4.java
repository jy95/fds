package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractFrequencyFrequencyMax;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

public class FrequencyFrequencyMaxR4 extends AbstractFrequencyFrequencyMax<FDSConfigR4, Dosage> {

    public FrequencyFrequencyMaxR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasFrequency(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequency();
    }

    @Override
    protected boolean hasFrequencyMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequencyMax();
    }

    @Override
    protected String turnFrequencyAndFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMin = repeat.getFrequency();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyAndFrequencyMaxText(frequencyMin, frequencyMax);
    }

    @Override
    protected String turnFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyMaxText(frequencyMax);
    }

    @Override
    protected String turnFrequencyToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequency = repeat.getFrequency();
        return formatFrequencyText(frequency);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && (hasFrequency(dosage) || hasFrequencyMax(dosage));
    }
}