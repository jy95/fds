package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface FrequencyFrequencyMaxPeriodPeriodMax<C extends FDSConfig, D> extends Translator<C, D> {

    /**
     * Check if dosage contains some frequency fields (frequency / frequencyMax)
     * @param dosage The dosage to check
     * @return True if present, otherwise false
     */
    boolean hasFrequency(D dosage);

    /**
     * Check if dosage contains some period fields (period / periodMax)
     * @param dosage The dosage to check
     * @return True if present, otherwise false
     */
    boolean hasPeriod(D dosage);

    @Override
    default boolean isPresent(D dosage) {
        return hasFrequency(dosage) || hasPeriod(dosage);
    }

    @Override
    default CompletableFuture<String> convert(D dosage) {
        var frequencyPart = extractFrequency(dosage);
        var periodPart = extractPeriod(dosage);

        return frequencyPart.thenCombineAsync(periodPart, (freq, period) ->
                Stream.of(freq, period)
                        .filter(part -> !part.isEmpty())
                        .collect(Collectors.joining(" "))
        );
    }

    CompletableFuture<String> extractFrequency(D dosage);

    CompletableFuture<String> extractPeriod(D dosage);
}
