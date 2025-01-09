package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractBoundsDuration<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat boundsDurationMsg;

    // Constructor passes the config to the superclass
    public AbstractBoundsDuration(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsDuration");
        boundsDurationMsg = new MessageFormat(msg, getConfig().getLocale());
    }

}
