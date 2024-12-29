package jy95.fhir.r4.dosage.utils.translators;

import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

import java.util.concurrent.CompletableFuture;

public class PatientInstruction extends AbstractTranslator {

    public PatientInstruction(){
        super(DisplayOrder.PATIENT_INSTRUCTION);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getPatientInstruction);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return !dosage.getPatientInstruction().isEmpty();
    }
}
