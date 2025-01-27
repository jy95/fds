package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.extension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingRepeatExtensionR5 implements Translator<FDSConfigR5, Dosage> {

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return config
                .fromExtensionsToString(
                        dosage.getTiming().getRepeat().getExtension()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasExtension();
    }
}