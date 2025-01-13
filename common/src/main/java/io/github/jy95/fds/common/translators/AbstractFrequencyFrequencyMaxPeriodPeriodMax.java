package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An abstract class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractFrequencyFrequencyMaxPeriodPeriodMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    /**
     * Class implementing the parsing of "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    protected final AbstractTranslator<C, D> frequencyTranslator;
    /**
     * Class implementing the parsing of "timing.repeat.period" / "timing.repeat.periodMax"
     */
    protected final AbstractTranslator<C, D> periodTranslator;

    /**
     * Constructs a new {@code AbstractFrequencyPeriodTranslator} with the specified configuration and translators.
     *
     * @param config             the configuration object
     * @param frequencyTranslator the translator for frequency-related fields
     * @param periodTranslator    the translator for period-related fields
     */
    public AbstractFrequencyFrequencyMaxPeriodPeriodMax(C config,
                                             AbstractTranslator<C, D> frequencyTranslator,
                                             AbstractTranslator<C, D> periodTranslator) {
        super(config);
        this.frequencyTranslator = frequencyTranslator;
        this.periodTranslator = periodTranslator;
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D dosage) {
        CompletableFuture<String> frequencyPart = frequencyTranslator.isPresent(dosage)
                ? frequencyTranslator.convert(dosage)
                : CompletableFuture.completedFuture("");

        CompletableFuture<String> periodPart = periodTranslator.isPresent(dosage)
                ? periodTranslator.convert(dosage)
                : CompletableFuture.completedFuture("");

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) ->
                Stream.of(freq, period)
                        .filter(part -> !part.isEmpty())
                        .collect(Collectors.joining(" "))
        );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D dosage) {
        return frequencyTranslator.isPresent(dosage) || periodTranslator.isPresent(dosage);
    }
}
