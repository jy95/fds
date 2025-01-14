package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractAsNeededTest;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Locale;

public class AsNeededTest extends AbstractAsNeededTest<FDSConfigR4, Dosage> {

    @Override
    public Dosage generateAsNeededBoolean() {
        Dosage dosage = new Dosage();
        BooleanType flag = new BooleanType();
        flag.setValue(Boolean.TRUE);
        dosage.setAsNeeded(flag);
        return dosage;
    }

    @Override
    public Dosage generateAsNeededCodeableConcept() {
        Dosage dosage = new Dosage();
        CodeableConcept neededFor = new CodeableConcept();
        neededFor.setText("head pain");
        dosage.setAsNeeded(neededFor);
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
