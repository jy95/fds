package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for translating "timing.repeat.frequency" /
 * "timing.repeat.frequencyMax" / "timing.repeat.period" /
 * "timing.repeat.periodMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMaxPeriodPeriodMax<D> implements Translator<D> {

    /* Translator for frequency fields */
    private final Translator<D> frequencyTranslator;
    /* Translator for period fields */
    private final Translator<D> periodTranslator;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D data) {
        return GenericOperations.anyMatchLazy(() -> frequencyTranslator.isPresent(data), () -> periodTranslator.isPresent(data));
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D data) {
        var frequencyPart = extractFrequency(data);
        var periodPart = extractPeriod(data);

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) -> Stream.of(freq, period)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /**
     * This method extracts the frequency part of the timing repeat component. It checks if the frequency field is present and, if so, converts it. If not, it returns an empty string.
     *
     * @param data a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    private CompletableFuture<String> extractFrequency(D data) {
        return GenericOperations.conditionalSelect(
            frequencyTranslator.isPresent(data),
            () -> frequencyTranslator.convert(data),
            () -> CompletableFuture.completedFuture("")
        );
    }

    /**
     * This method extracts the period part of the timing repeat component. It checks if the period field is present and, if so, converts it. If not, it returns an empty string.
     *
     * @param data a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    private CompletableFuture<String> extractPeriod(D data) {
        return GenericOperations.conditionalSelect(
            periodTranslator.isPresent(data),
            () -> periodTranslator.convert(data),
            () -> CompletableFuture.completedFuture("")
        );
    }
}
