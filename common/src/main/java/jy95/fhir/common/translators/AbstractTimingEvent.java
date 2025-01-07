package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractTimingEvent<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat timingEventMsg;

    public AbstractTimingEvent(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.event");
        timingEventMsg = new MessageFormat(msg, locale);
    }
}
