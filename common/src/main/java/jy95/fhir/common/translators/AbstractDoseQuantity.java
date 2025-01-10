package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.doseQuantity".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractDoseQuantity<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    /** MessageFormat instance used for "doseQuantity" translation. */
    protected final MessageFormat doseQuantityMsg;

    /**
     * Constructor for {@code AbstractDoseQuantity}.
     * @param config The configuration object used for translation.
     */
    public AbstractDoseQuantity(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.doseQuantity");
        doseQuantityMsg = new MessageFormat(msg, locale);
    }
}
