package io.github.jy95.common.types;

import java.util.Map;

/**
 * Abstract class representing a registry for handling dose and rate extractors.
 * This class allows mapping {@link DoseAndRateKey} instances to corresponding
 * {@link DoseAndRateExtractor} implementations.
 *
 * @param <D> the data type used by the {@link DoseAndRateExtractor}
 * @param <T> the result type returned by the {@link DoseAndRateExtractor}
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
     * @param extractorMap a map of {@link DoseAndRateKey} to {@link DoseAndRateExtractor}
     *                     instances used to retrieve extractors based on keys
     */
    public AbstractDoseAndRateRegistry(Map<DoseAndRateKey, DoseAndRateExtractor<D, T>> extractorMap) {
        this.extractors = extractorMap;
    }

    /**
     * Retrieves the {@link DoseAndRateExtractor} associated with the given {@link DoseAndRateKey}.
     *
     * @param key the {@link DoseAndRateKey} used to locate the extractor
     * @return the corresponding {@link DoseAndRateExtractor} for the given key, or {@code null} if no match is found
     */
    public DoseAndRateExtractor<D, T> getExtractor(DoseAndRateKey key) {
        return extractors.get(key);
    }
}
