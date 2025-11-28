package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Timing;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.code"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingCodeR4 implements Translator<Timing> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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
