package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.Translator;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "patientInstruction"
 *
 * @author jy95
 */
public class PatientInstructionR4 implements Translator<Dosage> {

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getPatientInstruction);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasPatientInstruction();
    }
}
