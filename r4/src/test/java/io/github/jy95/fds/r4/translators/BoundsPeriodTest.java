package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractBoundsPeriodTest;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoundsPeriodTest extends AbstractBoundsPeriodTest<FDSConfigR4, Dosage> {

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
    public DosageAPI<FDSConfigR4, Dosage> getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR4(FDSConfigR4.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    @Override
    public DosageAPI<FDSConfigR4, Dosage> getDosageAPI(FDSConfigR4 config) {
        return new DosageAPIR4(config);
    }

    @Override
    public Dosage generateEmptyDosage() {
        return new Dosage();
    }
}
