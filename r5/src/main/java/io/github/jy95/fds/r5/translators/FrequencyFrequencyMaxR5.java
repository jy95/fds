package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.FrequencyFrequencyMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

/**
 * R5 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxR5 implements FrequencyFrequencyMax<TimingRepeatComponent, FDSConfigR5> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequency(TimingRepeatComponent data) {
        return data.hasFrequency();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequencyMax(TimingRepeatComponent data) {
        return data.hasFrequencyMax();
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyAndFrequencyMaxToString(TimingRepeatComponent data) {
        var frequencyMin = data.getFrequency();
        var frequencyMax = data.getFrequencyMax();
        return formatFrequencyAndFrequencyMaxText(frequencyMin, frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyMaxToString(TimingRepeatComponent data) {
        var frequencyMax = data.getFrequencyMax();
        return formatFrequencyMaxText(frequencyMax);
    }

    /** {@inheritDoc} */
    @Override
    public String turnFrequencyToString(TimingRepeatComponent data) {
        var frequency = data.getFrequency();
        return formatFrequencyText(frequency);
    }
}
