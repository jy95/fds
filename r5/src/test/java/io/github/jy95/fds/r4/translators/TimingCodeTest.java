package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractTimingCodeTest;
import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Coding;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class TimingCodeTest extends AbstractTimingCodeTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithTimingCodeText() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Take medication in the morning on weekends and days off work");
        Timing timing = new Timing();
        timing.setCode(cc1);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithTimingCodeCodeAndDisplay() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/timing-abbreviation", "BID", "Two times a day at institution specified time")
                )
        );
        Timing timing = new Timing();
        timing.setCode(cc1);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithTimingCodeCodeOnly() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/timing-abbreviation", "BID", null)
                )
        );
        Timing timing = new Timing();
        timing.setCode(cc1);
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
