package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class PatientInstructionR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    public PatientInstructionR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getPatientInstruction);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasPatientInstruction();
    }
}