package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractFrequencyFrequencyMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
public class FrequencyFrequencyMaxR4 extends AbstractFrequencyFrequencyMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code FrequencyFrequencyMaxR4}.
     *
     * @param config The configuration object used for translation.
     */
    public FrequencyFrequencyMaxR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasFrequency(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequency();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasFrequencyMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequencyMax();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && (hasFrequency(dosage) || hasFrequencyMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    protected String turnFrequencyAndFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMin = repeat.getFrequency();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyAndFrequencyMaxText(frequencyMin, frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    protected String turnFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyMaxText(frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    protected String turnFrequencyToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequency = repeat.getFrequency();
        return formatFrequencyText(frequency);
    }
}
