package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractPeriodPeriodMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat periodMaxMsg;
    protected final MessageFormat periodMsg;
    
    public AbstractPeriodPeriodMax(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.periodMax");
        var msg2 = bundle.getString("fields.period");
        periodMaxMsg = new MessageFormat(msg1, locale);
        periodMsg = new MessageFormat(msg2, locale);
    }
}
