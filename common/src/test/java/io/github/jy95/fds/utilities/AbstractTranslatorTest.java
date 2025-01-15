package io.github.jy95.fds.utilities;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;

import java.util.Locale;

/**
 * Abstract translator test to have a boilerplate code for the test
 * @param <C> The configuration to use
 * @param <D> The dosage type
 */
public abstract class AbstractTranslatorTest<C extends FDSConfig, D> extends LocaleProviderBase {

    /**
     * Returns a instance that is configured for a given locale and field
     * @param locale The language to use
     * @param displayOrder To set up the instance with only one given field to translate
     * @return A DosageAPI instance that is fine-tuned for the need
     */
    public abstract DosageAPI<C, D> getDosageAPI(Locale locale, DisplayOrder displayOrder);

    /**
     * Returns a instance that is configured for a given config
     * @param config The configuration to use
     * @return A DosageAPI instance that is fine-tuned for the need
     */
    public abstract DosageAPI<C, D> getDosageAPI(C config);

    /**
     * Returns an empty Dosage instance for testing correct emptiness checks
     * @return a Dosage instance with all fields null
     */
    public abstract D generateEmptyDosage();
}
