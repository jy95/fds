package jy95.fhir.r4.dosage.utils.translators;

import lombok.Getter;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

public class PatientInstruction extends AbstractTranslator {
    @Getter
    private final DisplayOrder field;

    public PatientInstruction(){
        this.field = DisplayOrder.PATIENT_INSTRUCTION;
    }

    public String convert(Dosage dosage) {
        return dosage.getPatientInstruction();
    }

    public boolean isPresent(Dosage dosage) {
        return !dosage.getPatientInstruction().isEmpty();
    }
}
