package io.github.jy95.fds.common.types;

/**
 * A functional interface for extracting a dose and rate component from a given input.
 * @param <A> the type of the input from which the dose and rate will be extracted
 * @param <B> the type of the extracted dose and rate
 */
@FunctionalInterface
public interface DoseAndRateExtractor<A, B> {
    /**
     * Extracts the dose and rate component from the provided input.
     * @param doseAndRateComponent the input from which the dose and rate will be extracted
     * @return the extracted dose and rate
     */
    B extract(A doseAndRateComponent);
}