package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractBoundsRangeTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Range;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class BoundsRangeTest extends AbstractBoundsRangeTest<FDSConfigR5, Dosage> {

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
