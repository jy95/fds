package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.FrequencyFrequencyMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxR4 implements FrequencyFrequencyMax<Dosage, FDSConfigR4> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequency(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequency();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequencyMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasFrequencyMax();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && (hasFrequency(dosage) || hasFrequencyMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyAndFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMin = repeat.getFrequency();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyAndFrequencyMaxText(frequencyMin, frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequencyMax = repeat.getFrequencyMax();
        return formatFrequencyMaxText(frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var frequency = repeat.getFrequency();
        return formatFrequencyText(frequency);
    }
}
