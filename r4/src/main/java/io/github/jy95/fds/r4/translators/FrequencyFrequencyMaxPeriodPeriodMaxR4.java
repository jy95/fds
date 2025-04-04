package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.FrequencyFrequencyMaxPeriodPeriodMax;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxPeriodPeriodMaxR4 implements FrequencyFrequencyMaxPeriodPeriodMax<FDSConfigR4, Dosage> {

    /**
     * Class implementing the parsing of "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    protected final Translator<FDSConfigR4, Dosage> frequencyTranslator;
    /**
     * Class implementing the parsing of "timing.repeat.period" / "timing.repeat.periodMax"
     */
    protected final Translator<FDSConfigR4, Dosage> periodTranslator;

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequency(Dosage dosage) {
        return frequencyTranslator.isPresent(dosage);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriod(Dosage dosage) {
        return periodTranslator.isPresent(dosage);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> extractFrequency(Dosage dosage) {
        return frequencyTranslator.isPresent(dosage)
                ? frequencyTranslator.convert(dosage)
                : CompletableFuture.completedFuture("");
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> extractPeriod(Dosage dosage) {
        return periodTranslator.isPresent(dosage)
                ? periodTranslator.convert(dosage)
                : CompletableFuture.completedFuture("");
    }
}
