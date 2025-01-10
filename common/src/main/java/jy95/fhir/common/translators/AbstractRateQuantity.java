package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.rateQuantity".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractRateQuantity<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    /** MessageFormat instance used for "rateQuantity" translation. */
    protected final MessageFormat rateQuantityMsg;

    /**
     * Constructor for {@code AbstractRateQuantity}.
     * @param config The configuration object used for translation.
     */
    public AbstractRateQuantity(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateQuantity");
        rateQuantityMsg = new MessageFormat(msg, locale);
    }
}
