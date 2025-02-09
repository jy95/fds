package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.DurationDurationMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * R5 class for translating "timing.repeat.duration" / "timing.repeat.durationMax"
 *
 * @author jy95
 */
public class DurationDurationMaxR5 implements DurationDurationMax<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "duration" translation. */
    protected final MessageFormat durationMsg;
    /** MessageFormat instance used for "duration" &amp; "durationMax" translation */
    protected final MessageFormat durationMaxMsg;

    /**
     * The locale for translation.
     */
    private final Locale locale;

    /**
     * Constructor for {@code DurationDurationMaxR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public DurationDurationMaxR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.locale = config.getLocale();
        this.durationMsg = getDurationMsg(bundle, locale);
        this.durationMaxMsg = getDurationMaxMsg(bundle, locale);
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

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
