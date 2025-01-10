package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

/**
 * An abstract class for translating "timing.repeat.boundsRange".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractDoseRange<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "doseRange" translation. */
    protected final MessageFormat doseRangeMsg;

    /**
     * Constructor for {@code AbstractDoseRange}.
     * @param config The configuration object used for translation.
     */
    public AbstractDoseRange(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.doseRange");
        doseRangeMsg = new MessageFormat(msg, locale);
    }
    
}
