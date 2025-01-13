package io.github.jy95.fds.common.types;

import java.util.Map;

/**
 * Abstract class representing a registry for handling dose and rate extractors.
 * This class allows mapping {@link io.github.jy95.fds.common.types.DoseAndRateKey} instances to corresponding
 * {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} implementations.
 *
 * @param <D> the data type used by the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor}
 * @param <T> the result type returned by the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor}
 * @author jy95
 */
public abstract class AbstractDoseAndRateRegistry<D, T> {

    /**
     * A map that associates {@link DoseAndRateKey} instances with their corresponding
     * {@link DoseAndRateExtractor} implementations.
     */
    private final Map<DoseAndRateKey, DoseAndRateExtractor<D, T>> extractors;

    /**
     * Constructs a new {@code AbstractDoseAndRateRegistry} with the given extractor map.
     *
     * @param extractorMap a map of {@link io.github.jy95.fds.common.types.DoseAndRateKey} to {@link io.github.jy95.fds.common.types.DoseAndRateExtractor}
     *                     instances used to retrieve extractors based on keys
     */
    public AbstractDoseAndRateRegistry(Map<DoseAndRateKey, DoseAndRateExtractor<D, T>> extractorMap) {
        this.extractors = extractorMap;
    }

    /**
     * Retrieves the {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} associated with the given {@link io.github.jy95.fds.common.types.DoseAndRateKey}.
     *
     * @param key the {@link io.github.jy95.fds.common.types.DoseAndRateKey} used to locate the extractor
     * @return the corresponding {@link io.github.jy95.fds.common.types.DoseAndRateExtractor} for the given key, or {@code null} if no match is found
     */
    public DoseAndRateExtractor<D, T> getExtractor(DoseAndRateKey key) {
        return extractors.get(key);
    }
}
