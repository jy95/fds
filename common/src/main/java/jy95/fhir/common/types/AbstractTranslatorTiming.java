package jy95.fhir.common.types;

import jy95.fhir.common.config.FDSConfig;

public abstract class AbstractTranslatorTiming<C extends FDSConfig, D> extends AbstractTranslator<C, D>{

    public AbstractTranslatorTiming(C config) {
        super(config);
    }

    // To check if dosage object truly has a "Timing" object
    protected abstract boolean hasTiming(D dosage);

    // To check if timing object truly has the required condition for this translator
    protected abstract boolean hasRequiredElements(D dosage);

    @Override
    public boolean isPresent(D dosage) {
        return hasTiming(dosage) && hasRequiredElements(dosage);
    }
}
