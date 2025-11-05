package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.FrequencyFrequencyMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxR4 implements FrequencyFrequencyMax<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax) {
        Map<String, Object> arguments = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );
        var frequencyAndFrequencyMaxMsg = translationService.getMessage(KEY_FREQUENCY_AND_FREQUENCY_MAX);
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    /** {@inheritDoc} */
    @Override
    public String formatFrequencyMaxText(int frequencyMax) {
        var frequencyMaxMsg = translationService.getMessage(KEY_FREQUENCY_MAX);
        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    /** {@inheritDoc} */
    @Override
    public String formatFrequencyText(int frequency) {
        var frequencyMsg = translationService.getMessage(KEY_FREQUENCY);
        return frequencyMsg.format(new Object[]{frequency});
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
