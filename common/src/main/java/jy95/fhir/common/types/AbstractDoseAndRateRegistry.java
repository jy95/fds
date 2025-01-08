package jy95.fhir.common.types;

import java.util.Map;

public abstract class AbstractDoseAndRateRegistry<D, T> {
    private final Map<DoseAndRateKey, DoseAndRateExtractor<D, T>> extractors;

    public AbstractDoseAndRateRegistry(Map<DoseAndRateKey, DoseAndRateExtractor<D, T>> extractorMap) {
        this.extractors = extractorMap;
    }

    public DoseAndRateExtractor<D, T> getExtractor(DoseAndRateKey key) {
        return extractors.get(key);
    }
}
