package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

/**
 * An abstract class for translating "maxDosePerAdministration".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractMaxDosePerAdministration<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "maxDosePerAdministration" translation */
    protected final MessageFormat maxDosePerAdministrationMsg;

    /**
     * Constructor for {@code AbstractMaxDosePerAdministration}.
     * @param config The configuration object used for translation.
     */
    public AbstractMaxDosePerAdministration(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.maxDosePerAdministration");
        maxDosePerAdministrationMsg = new MessageFormat(msg, locale);
    }
    
}
