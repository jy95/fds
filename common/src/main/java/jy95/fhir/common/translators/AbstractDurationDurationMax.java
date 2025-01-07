package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractDurationDurationMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat durationMsg;
    protected final MessageFormat durationMaxMsg;

    public AbstractDurationDurationMax(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.duration");
        var msg2 = bundle.getString("fields.durationMax");
        durationMsg = new MessageFormat(msg1, locale);
        durationMaxMsg = new MessageFormat(msg2, locale);
    }
    
}
