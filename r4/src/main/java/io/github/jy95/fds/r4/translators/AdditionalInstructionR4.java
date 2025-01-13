package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractAdditionalInstruction;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "additionalInstruction".
 *
 * @author jy95
 */
public class AdditionalInstructionR4 extends AbstractAdditionalInstruction<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code AdditionalInstructionR4}.
     *
     * @param config The configuration object used for translation.
     */
    public AdditionalInstructionR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var additionalInstructions = dosage
                .getAdditionalInstruction()
                .stream()
                .map(ins -> this.getConfig().fromCodeableConceptToString(ins))
                .toList();

        return instructionsFuture(additionalInstructions);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAdditionalInstruction();
    }
}
