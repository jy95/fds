package io.github.jy95.fds.r5.utils.adapters;

import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Timing;

import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Adapter that allows a Translator<Timing> to be used as a Translator<Dosage>.
 * It handles the extraction of the Timing object from the Dosage object.
 */
@RequiredArgsConstructor
public class TimingTranslatorAdapterR5 implements Translator<Dosage> {

    /**
     * The Timing translator we have
     */
    private final Translator<Timing> timingTranslator;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var timing = dosage.getTiming();
        return timingTranslator.convert(timing);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        if (!dosage.hasTiming()) {
            return false;
        }

        var timing = dosage.getTiming();
        return timingTranslator.isPresent(timing);
    }

}
