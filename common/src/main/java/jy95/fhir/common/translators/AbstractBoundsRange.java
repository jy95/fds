package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

public abstract class AbstractBoundsRange<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    protected final MessageFormat boundsRangeMsg;

    public AbstractBoundsRange(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsRange");
        boundsRangeMsg = new MessageFormat(msg, getConfig().getLocale());
    }
}
