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
     * Check if dosage contains some frequency fields (frequency / frequencyMax)
     *
     * @param dosage The dosage to check
     * @return True if present, otherwise false
     */
    boolean hasFrequency(D dosage);

    /**
     * Check if dosage contains some period fields (period / periodMax)
     *
     * @param dosage The dosage to check
     * @return True if present, otherwise false
     */
    boolean hasPeriod(D dosage);

    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D dosage) {
        return hasFrequency(dosage) || hasPeriod(dosage);
    }

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D dosage) {
        var frequencyPart = extractFrequency(dosage);
        var periodPart = extractPeriod(dosage);

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) -> Stream.of(freq, period)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.joining(" ")));
    }

    /**
     * <p>
     * extractFrequency.
     * </p>
     *
     * @param dosage a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    CompletableFuture<String> extractFrequency(D dosage);

    /**
     * <p>
     * extractPeriod.
     * </p>
     *
     * @param dosage a D object
     * @return a {@link java.util.concurrent.CompletableFuture} object
     */
    CompletableFuture<String> extractPeriod(D dosage);
}
