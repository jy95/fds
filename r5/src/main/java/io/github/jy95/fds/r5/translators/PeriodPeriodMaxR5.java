package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;
import io.github.jy95.fds.common.translators.PeriodPeriodMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * R5 class for translating "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
public class PeriodPeriodMaxR5 implements PeriodPeriodMax<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "period" &amp; "periodMax" translation */
    protected final MessageFormat periodMaxMsg;
    /** MessageFormat instance used for "period" */
    protected final MessageFormat periodMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * Constructor for {@code PeriodPeriodMaxR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public PeriodPeriodMaxR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.config = config;
        this.periodMaxMsg = getPeriodMaxMsg(bundle, config.getLocale());
        this.periodMsg = getPeriodMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriod();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriodMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriodMax();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && (hasPeriod(dosage) || hasPeriodMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public String turnPeriodAndPeriodMaxToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var periodMax = repeat.getPeriodMax();
        var periodMin = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitText = UnitsOfTimeFormatter.formatWithoutCount(config.getLocale(), periodUnit, periodMax);
        return formatPeriodAndPeriodMaxText(periodMin, periodMax, unitText);
    }

    /** {@inheritDoc} */
    @Override
    public String turnPeriodToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var period = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

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
        return periodMaxMsg.format(arguments);
    }
}
