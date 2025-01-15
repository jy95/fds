package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractTimingCodeTest;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;

import java.util.List;
import java.util.Locale;

public class TimingCodeTest extends AbstractTimingCodeTest<FDSConfigR4, Dosage> {

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
