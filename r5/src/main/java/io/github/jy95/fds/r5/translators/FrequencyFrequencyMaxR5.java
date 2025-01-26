package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.FrequencyFrequencyMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * R5 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax"
 *
 * @author jy95
 */
public class FrequencyFrequencyMaxR5 implements FrequencyFrequencyMax<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "frequency" &amp; "frequencyMax" translation */
    protected final MessageFormat frequencyAndFrequencyMaxMsg;
    /** MessageFormat instance used for "frequencyMax" translation */
    protected final MessageFormat frequencyMaxMsg;
    /** MessageFormat instance used for "frequencyMax" translation */
    protected final MessageFormat frequencyMsg;

    /**
     * Constructor for {@code FrequencyFrequencyMaxR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public FrequencyFrequencyMaxR5(FDSConfigR5 config, ResourceBundle bundle) {
        var locale = config.getLocale();
        this.frequencyAndFrequencyMaxMsg = getFrequencyAndFrequencyMaxMsg(bundle, locale);
        this.frequencyMaxMsg = getFrequencyMaxMsg(bundle, locale);
        this.frequencyMsg = getFrequencyMsg(bundle, locale);
    }

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
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    /** {@inheritDoc} */
    @Override
    public String formatFrequencyMaxText(int frequencyMax) {
        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    /** {@inheritDoc} */
    @Override
    public String formatFrequencyText(int frequency) {
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
