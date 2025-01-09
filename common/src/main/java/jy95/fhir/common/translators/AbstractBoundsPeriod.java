package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

public abstract class AbstractBoundsPeriod<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    protected final MessageFormat boundsPeriodMsg;

    public AbstractBoundsPeriod(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsPeriod");
        boundsPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }
}
