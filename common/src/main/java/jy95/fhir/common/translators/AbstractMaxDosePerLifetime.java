package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractMaxDosePerLifetime<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat maxDosePerLifetimeMsg;

    public AbstractMaxDosePerLifetime(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.maxDosePerLifetime");
        maxDosePerLifetimeMsg = new MessageFormat(msg, locale);
    }
    
}
