package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractAsNeeded<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat asNeededForMsg;
    protected final String asNeededMsg;

    // Constructor passes the config to the superclass
    public AbstractAsNeeded(C config) {
        super(config);
        var bundle = getResources();
        var msg = bundle.getString("fields.asNeededFor");
        asNeededForMsg = new MessageFormat(msg, this.getConfig().getLocale());
        asNeededMsg = bundle.getString("fields.asNeeded");
    }
}
