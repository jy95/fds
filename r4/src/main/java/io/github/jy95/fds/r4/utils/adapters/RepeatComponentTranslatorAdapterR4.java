package io.github.jy95.fds.r4.utils.adapters;

import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Adapter that allows a TimingRepeatComponent Translator to be used as a Dosage Translator.
 * It handles the required navigation and extraction: Dosage -> Timing -> Repeat.
 */
@RequiredArgsConstructor
public class RepeatComponentTranslatorAdapterR4 implements Translator<Dosage> {

    /**
     * The timing repeat translator
     */
    private final Translator<TimingRepeatComponent> repeatComponentTranslator;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var repeatComponent = dosage.getTiming().getRepeat();
        return repeatComponentTranslator.convert(repeatComponent);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        if (!dosage.hasTiming() || !dosage.getTiming().hasRepeat()) {
            return false;
        }

        var repeatComponent = dosage.getTiming().getRepeat();
        return repeatComponentTranslator.isPresent(repeatComponent);
    }
}
