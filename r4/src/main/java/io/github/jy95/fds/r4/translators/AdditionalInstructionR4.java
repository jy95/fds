package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.AdditionalInstruction;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "additionalInstruction".
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class AdditionalInstructionR4 implements AdditionalInstruction<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var additionalInstructions = dosage
                .getAdditionalInstruction()
                .stream()
                .map(config::fromCodeableConceptToString)
                .toList();

        return instructionsFuture(translationService, additionalInstructions);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAdditionalInstruction();
    }
}
