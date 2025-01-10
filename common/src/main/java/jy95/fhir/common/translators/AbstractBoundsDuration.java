package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

/**
 * An abstract class for translating "timing.repeat.boundsDuration".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractBoundsDuration<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "boundsDuration" translation. */
    protected final MessageFormat boundsDurationMsg;

    /**
     * Constructor for {@code AbstractBoundsDuration}.
     * @param config The configuration object used for translation.
     */
    public AbstractBoundsDuration(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsDuration");
        boundsDurationMsg = new MessageFormat(msg, getConfig().getLocale());
    }

}
