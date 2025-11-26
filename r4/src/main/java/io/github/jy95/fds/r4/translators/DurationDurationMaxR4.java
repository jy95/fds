package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.timing.repeat.DurationDurationMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

/**
 * R4 class for translating "timing.repeat.duration" / "timing.repeat.durationMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class DurationDurationMaxR4 implements DurationDurationMax<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasDuration(TimingRepeatComponent data) {
        return data.hasDuration();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDurationMax(TimingRepeatComponent data) {
        return data.hasDurationMax();
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationToString(TimingRepeatComponent data) {
        var durationUnit = data.getDurationUnit().toCode();
        var durationQuantity = data.getDuration();

        var locale = translationService.getConfig().getLocale();
        var durationMsg = translationService.getMessage(KEY_DURATION);

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    public String turnDurationMaxToString(TimingRepeatComponent data) {
        var durationUnit = data.getDurationUnit().toCode();
        var durationQuantity = data.getDurationMax();

        var locale = translationService.getConfig().getLocale();
        var durationMaxMsg = translationService.getMessage(KEY_DURATION_MAX);

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDurationUnit(TimingRepeatComponent data) {
        return data.hasDurationUnit();
    }
}
