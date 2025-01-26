package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractAdditionalInstructionTest;
import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Dosage;

import java.util.List;
import java.util.Locale;

public class AdditionalInstructionTest extends AbstractAdditionalInstructionTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage getDosageWithSingleInstruction() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Instruction 1");
        dosage.setAdditionalInstruction(List.of(cc1));
        return dosage;
    }

    @Override
    protected Dosage getDosageMultipleAdditionalInstruction() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Instruction 1");
        CodeableConcept cc2 = new CodeableConcept();
        cc2.setText("Instruction 2");
        dosage.setAdditionalInstruction(List.of(cc1, cc2));
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
