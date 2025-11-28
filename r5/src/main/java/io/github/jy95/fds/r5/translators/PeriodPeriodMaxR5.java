package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.timing.repeat.PeriodPeriodMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.math.BigDecimal;
import java.util.Map;

/**
 * R5 class for translating "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class PeriodPeriodMaxR5 implements PeriodPeriodMax<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriod(TimingRepeatComponent data) {
        return data.hasPeriod();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriodMax(TimingRepeatComponent data) {
        return data.hasPeriodMax();
    }

    /** {@inheritDoc} */
    @Override
    public String turnPeriodAndPeriodMaxToString(TimingRepeatComponent data) {

        var periodMax = data.getPeriodMax();
        var periodMin = data.getPeriod();
        var periodUnit = data.getPeriodUnit().toCode();

        var config = translationService.getConfig();

        var unitText = UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), periodUnit, periodMax);
        return formatPeriodAndPeriodMaxText(periodMin, periodMax, unitText);
    }

    /** {@inheritDoc} */
    @Override
    public String turnPeriodToString(TimingRepeatComponent data) {

        var period = data.getPeriod();
        var periodUnit = data.getPeriodUnit().toCode();
        var config = translationService.getConfig();

        var unitText = UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), periodUnit, period);
        return formatPeriodText(period, unitText);
    }

    /**
     * Formats a single-period value with its unit.
     *
     * @param period The period value.
     * @param unitText The localized unit of the period.
     * @return A formatted string representing the period and its unit.
     */
    private String formatPeriodText(BigDecimal period, String unitText){
        Map<String, Object> arguments = Map.of(
                "period", period,
                "periodUnit",unitText
        );
        var periodMsg = translationService.getMessage(KEY_PERIOD);
        return periodMsg.format(arguments);
    }

    /**
     * Formats both period and periodMax values with their shared unit.
     *
     * @param periodMin The minimum period value.
     * @param periodMax The maximum period value.
     * @param unitText The localized unit of the periods.
     * @return A formatted string representing the range of periods and their unit.
     */
    private String formatPeriodAndPeriodMaxText(BigDecimal periodMin, BigDecimal periodMax, String unitText) {
        Map<String, Object> arguments = Map.of(
                "maxPeriod", periodMax,
                "minPeriod", periodMin,
                "unit",unitText
        );
        var periodMaxMsg = translationService.getMessage(KEY_PERIOD_MAX);
        return periodMaxMsg.format(arguments);
    }
}
