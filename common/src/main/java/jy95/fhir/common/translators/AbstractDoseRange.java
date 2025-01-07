package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractDoseRange<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat doseRangeMsg;
    
    public AbstractDoseRange(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.doseRange");
        doseRangeMsg = new MessageFormat(msg, locale);
    }
    
}
