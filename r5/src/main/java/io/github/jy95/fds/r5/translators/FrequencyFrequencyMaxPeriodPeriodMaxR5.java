package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.translators.timing.repeat.FrequencyFrequencyMaxPeriodPeriodMax;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxPeriodPeriodMaxR5 implements FrequencyFrequencyMaxPeriodPeriodMax<TimingRepeatComponent> {

    /**
     * Class implementing the parsing of "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    protected final Translator<TimingRepeatComponent> frequencyTranslator;
    /**
     * Class implementing the parsing of "timing.repeat.period" / "timing.repeat.periodMax"
     */
    protected final Translator<TimingRepeatComponent> periodTranslator;

    /** {@inheritDoc} */
    @Override
    public boolean hasFrequency(TimingRepeatComponent data) {
        return frequencyTranslator.isPresent(data);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasPeriod(TimingRepeatComponent data) {
        return periodTranslator.isPresent(data);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> extractFrequency(TimingRepeatComponent data) {
        return GenericOperations.conditionalSelect(
            frequencyTranslator.isPresent(data),
            () -> frequencyTranslator.convert(data),
            () -> CompletableFuture.completedFuture("")
        );
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> extractPeriod(TimingRepeatComponent data) {
        return GenericOperations.conditionalSelect(
            periodTranslator.isPresent(data),
            () -> periodTranslator.convert(data),
            () -> CompletableFuture.completedFuture("")
        );
    }
}
