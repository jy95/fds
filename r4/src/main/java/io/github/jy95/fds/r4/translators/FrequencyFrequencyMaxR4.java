package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.FrequencyFrequencyMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxR4 implements FrequencyFrequencyMax<TimingRepeatComponent, FDSConfigR4> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR4> translationService;

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
    public boolean isPresent(TimingRepeatComponent data) {
        return hasFrequency(data) || hasFrequencyMax(data);
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
