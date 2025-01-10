package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.rateRange".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractRateRange<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    /** MessageFormat instance used for "rateRange" translation. */
    protected final MessageFormat rateRangeMsg;

    /**
     * Constructor for {@code AbstractRateRange}.
     * @param config The configuration object used for translation.
     */
    public AbstractRateRange(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateRange");
        rateRangeMsg = new MessageFormat(msg, locale);
    }
}
