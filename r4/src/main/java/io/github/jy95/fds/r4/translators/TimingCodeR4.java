package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.code"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingCodeR4 implements TranslatorTiming<FDSConfigR4, Dosage> {

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return config
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
