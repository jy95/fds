package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractBoundsDurationTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Duration;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class BoundsDurationTest extends AbstractBoundsDurationTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithBoundsDuration() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Quantity duration = new Duration();
        duration.setValue(3);
        duration.setCode("d");
        duration.setSystem("http://hl7.org/fhir/ValueSet/duration-units");
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setBounds(duration);
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
