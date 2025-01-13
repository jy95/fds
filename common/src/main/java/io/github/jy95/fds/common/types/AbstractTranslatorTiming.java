package io.github.jy95.fds.common.types;

import io.github.jy95.fds.common.config.FDSConfig;

/**
 * Abstract class for translators that specifically handle dosage objects with "Timing" elements.
 * This class extends {@link io.github.jy95.fds.common.types.AbstractTranslator} to provide additional checks for timing-related data.
 *
 * @param <C> the type of configuration extending {@link io.github.jy95.fds.common.config.FDSConfig}
 * @param <D> the type of dosage field to be translated
 * @author jy95
 */
public abstract class AbstractTranslatorTiming<C extends FDSConfig, D> extends AbstractTranslator<C, D>{

    /**
     * Constructs a new {@code AbstractTranslatorTiming} with the specified configuration.
     *
     * @param config the configuration object providing locale and resource bundle
     */
    public AbstractTranslatorTiming(C config) {
        super(config);
    }

    /**
     * Checks if the given dosage object contains a "Timing" element.
     *
     * @param dosage the dosage object to check
     * @return {@code true} if the dosage object has a "Timing" element, {@code false} otherwise
     */
    protected abstract boolean hasTiming(D dosage);

    /**
     * Checks if the "Timing" element of the given dosage object contains the required conditions
     * for this translator.
     *
     * @param dosage the dosage object to check
     * @return {@code true} if the "Timing" element has the required conditions, {@code false} otherwise
     */
    protected abstract boolean hasRequiredElements(D dosage);

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D dosage) {
        return hasTiming(dosage) && hasRequiredElements(dosage);
    }
}
