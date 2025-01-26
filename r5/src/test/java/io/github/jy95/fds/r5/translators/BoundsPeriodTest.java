package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractBoundsPeriodTest;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Period;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class BoundsPeriodTest extends AbstractBoundsPeriodTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateBothStartAndEnd() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setStartElement(new DateTimeType("2011-05-23"));
        boundsPeriod.setEndElement(new DateTimeType("2011-05-27"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyEnd() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setEndElement(new DateTimeType("2015-02-07T13:28:17"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyStart() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setStartElement(new DateTimeType("2011-05-23"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
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
