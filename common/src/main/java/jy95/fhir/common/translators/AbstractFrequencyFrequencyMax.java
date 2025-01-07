package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractFrequencyFrequencyMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat frequencyAndFrequencyMaxMsg;
    protected final MessageFormat frequencyMaxMsg;
    protected final MessageFormat frequencyMsg;

    public AbstractFrequencyFrequencyMax(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.frequencyAndFrequencyMax");
        var msg2 = bundle.getString("fields.frequencyMax");
        var msg3 = bundle.getString("fields.frequency");
        frequencyAndFrequencyMaxMsg = new MessageFormat(msg1, locale);
        frequencyMaxMsg = new MessageFormat(msg2, locale);
        frequencyMsg = new MessageFormat(msg3, locale);
    }
    
}
