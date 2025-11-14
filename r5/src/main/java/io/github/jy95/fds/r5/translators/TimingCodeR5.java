package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.TranslatorTiming;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.code"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingCodeR5 implements TranslatorTiming<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return translationService
                .getConfig()
                .fromCodeableConceptToString(
                        dosage.getTiming().getCode()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasCode();
    }
}
