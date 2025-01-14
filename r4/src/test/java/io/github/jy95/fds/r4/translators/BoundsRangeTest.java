package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractBoundsRangeTest;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundsRangeTest extends AbstractBoundsRangeTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateBothLowAndHighWithoutUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(1));
        boundsRange.setHigh(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateBothLowAndHighWithUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(1));
        boundsRange.setHigh(new Quantity(null, 3, "http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyHighWithoutUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setHigh(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyHighWithUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setHigh(new Quantity(null, 3,"http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyLowWithoutUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateOnlyLowWithUnit() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(null, 3, "http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
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
