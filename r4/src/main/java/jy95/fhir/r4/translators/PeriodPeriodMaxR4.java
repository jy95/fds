package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractPeriodPeriodMax;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

public class PeriodPeriodMaxR4 extends AbstractPeriodPeriodMax<FDSConfigR4, Dosage> {

    public PeriodPeriodMaxR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && timing.hasRepeat() && (hasPeriod(dosage) || hasPeriodMax(dosage));
    }

    @Override
    protected boolean hasPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriod();
    }

    @Override
    protected boolean hasPeriodMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasPeriodMax();
    }

    @Override
    protected String turnPeriodAndPeriodMaxToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var periodMax = repeat.getPeriodMax();
        var periodMin = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitText = getUnit(periodUnit, periodMax);
        return formatPeriodAndPeriodMaxText(periodMin, periodMax, unitText);
    }

    @Override
    protected String turnPeriodToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var period = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitText = getUnit(periodUnit, period);
        return formatPeriodText(period, unitText);
    }
}
