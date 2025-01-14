package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Locale;

import io.github.jy95.fds.translators.AbstractAdditionalInstructionTest;

public class AdditionalInstructionTest extends AbstractAdditionalInstructionTest<FDSConfigR4, Dosage> {

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
