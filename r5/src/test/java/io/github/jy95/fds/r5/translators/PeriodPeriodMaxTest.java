package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractPeriodPeriodMaxTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class PeriodPeriodMaxTest extends AbstractPeriodPeriodMaxTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithPeriodOnly() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithBothPeriod() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodMax(3);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    public DosageAPI<FDSConfigR5, Dosage> getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR5(FDSConfigR5.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    @Override
    public DosageAPI<FDSConfigR5, Dosage> getDosageAPI(FDSConfigR5 config) {
        return new DosageAPIR5(config);
    }

    @Override
    public Dosage generateEmptyDosage() {
        return new Dosage();
    }
}
