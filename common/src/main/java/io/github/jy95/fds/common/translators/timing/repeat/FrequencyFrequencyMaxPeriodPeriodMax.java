package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

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
 * @since 1.0.0
 */
public interface FrequencyFrequencyMaxPeriodPeriodMax<D> extends Translator<D> {

    /**
     * Check if data contains some frequency fields (frequency / frequencyMax)
     *
     * @param data The data to check
     * @return True if present, otherwise false
     */
    boolean hasFrequency(D data);

    /**
     * Check if data contains some period fields (period / periodMax)
     *
     * @param data The data to check
     * @return True if present, otherwise false
     */
    boolean hasPeriod(D data);

    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D data) {
        return hasFrequency(data) || hasPeriod(data);
    }

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D data) {
        var frequencyPart = extractFrequency(data);
        var periodPart = extractPeriod(data);

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) -> Stream.of(freq, period)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /**
     * <p>
     * extractFrequency.
     * </p>
     *
     * @param data a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    CompletableFuture<String> extractFrequency(D data);

    /**
     * <p>
     * extractPeriod.
     * </p>
     *
     * @param data a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    CompletableFuture<String> extractPeriod(D data);
}
