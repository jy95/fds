package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

public abstract class AbstractMaxDosePerPeriod<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat maxDosePerPeriodMsg;

    public AbstractMaxDosePerPeriod(C config) {
        super(config);
        String msg = getResources().getString("fields.maxDosePerPeriod");
        maxDosePerPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }

}
