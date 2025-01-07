package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractRateQuantity<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat rateQuantityMsg;

    public AbstractRateQuantity(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateQuantity");
        rateQuantityMsg = new MessageFormat(msg, locale);
    }
}
