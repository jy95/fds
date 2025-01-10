package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractAdditionalInstruction;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class AdditionalInstructionR4 extends AbstractAdditionalInstruction<FDSConfigR4, Dosage> {

    public AdditionalInstructionR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var additionalInstructions = dosage
                .getAdditionalInstruction()
                .stream()
                .map(this.getConfig().getFromCodeableConceptToString())
                .toList();

        return instructionsFuture(additionalInstructions);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAdditionalInstruction();
    }
}
