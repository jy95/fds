package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.timing.repeat.DurationDurationMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.duration" / "timing.repeat.durationMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class DurationDurationMaxR4 implements DurationDurationMax<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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

        var locale = translationService.getConfig().getLocale();
        var durationMsg = translationService.getMessage(KEY_DURATION);

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationMaxToString(Dosage dosage) {
        var repeat = dosage.getTiming().getRepeat();
        var durationUnit = repeat.getDurationUnit().toCode();
        var durationQuantity = repeat.getDurationMax();

        var locale = translationService.getConfig().getLocale();
        var durationMaxMsg = translationService.getMessage(KEY_DURATION_MAX);

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
