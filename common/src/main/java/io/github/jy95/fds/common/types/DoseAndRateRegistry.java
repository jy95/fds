package io.github.jy95.fds.common.types;

/**
 * Abstract class representing a registry for handling dose and rate extractors.
 * This class allows mapping {@link io.github.jy95.fds.common.types.DoseAndRateKey} instances to corresponding
 * {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} implementations.
 *
 * @param <D> the data type used by the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor}
 * @param <T> the result type returned by the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor}
 * @author jy95
 * @since 1.0.0
 */
public interface DoseAndRateRegistry<D, T> {

    /**
     * Retrieves the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} associated with the given {@link io.github.jy95.fds.common.types.DoseAndRateKey}.
     *
     * @param key the {@link io.github.jy95.fds.common.types.DoseAndRateKey} used to locate the extractor
     * @return the corresponding {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} for the given key, or {@code null} if no match is found
     */
    DoseAndRateExtractor<D, T> getExtractor(DoseAndRateKey key);
}
