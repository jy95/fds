package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractPeriodPeriodMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
public class PeriodPeriodMaxR4 extends AbstractPeriodPeriodMax<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code PeriodPeriodMaxR4}.
     *
     * @param config The configuration object used for translation.
     */
    public PeriodPeriodMaxR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriod();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasPeriodMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriodMax();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && timing.hasRepeat() && (hasPeriod(dosage) || hasPeriodMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected String turnPeriodAndPeriodMaxToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var periodMax = repeat.getPeriodMax();
        var periodMin = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitText = getUnit(periodUnit, periodMax);
        return formatPeriodAndPeriodMaxText(periodMin, periodMax, unitText);
    }

    /** {@inheritDoc} */
    @Override
    protected String turnPeriodToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var period = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitText = getUnit(periodUnit, period);
        return formatPeriodText(period, unitText);
    }
}
