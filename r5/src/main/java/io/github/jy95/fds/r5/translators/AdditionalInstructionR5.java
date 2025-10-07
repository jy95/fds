package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.translators.AdditionalInstruction;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * R5 class for translating "additionalInstruction".
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class AdditionalInstructionR5 implements AdditionalInstruction<FDSConfigR5, Dosage> {

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var additionalInstructions = dosage
                .getAdditionalInstruction()
                .stream()
                .map(config::fromCodeableConceptToString)
                .toList();

        return instructionsFuture(additionalInstructions, bundle);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAdditionalInstruction();
    }
}
