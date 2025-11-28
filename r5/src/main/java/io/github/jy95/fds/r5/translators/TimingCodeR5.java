package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Timing;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.code"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingCodeR5 implements Translator<Timing> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Timing data) {
        return translationService
                .getConfig()
                .fromCodeableConceptToString(
                        data.getCode()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Timing data) {
        return data.hasCode();
    }
}
