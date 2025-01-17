package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.DurationDurationMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;

/**
 * R4 class for translating "timing.repeat.duration" / "timing.repeat.durationMax"
 *
 * @author jy95
 */
public class DurationDurationMaxR4 implements DurationDurationMax<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "duration" translation. */
    protected final MessageFormat durationMsg;
    /** MessageFormat instance used for "duration" &amp; "durationMax" translation */
    protected final MessageFormat durationMaxMsg;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DurationDurationMaxR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DurationDurationMaxR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.durationMsg = getDurationMsg(bundle, config.getLocale());
        this.durationMaxMsg = getDurationMaxMsg(bundle, config.getLocale());
        this.bundle = bundle;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDuration(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDuration();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDurationMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasDurationMax();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasDurationUnit()
                && (hasDuration(dosage) || hasDurationMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDuration();

        var durationText = quantityToString(bundle, durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var durationText = quantityToString(bundle, durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
