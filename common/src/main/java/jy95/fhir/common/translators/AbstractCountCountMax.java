package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

import java.util.Map;

public abstract class AbstractCountCountMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat countMaxMsg;
    protected final MessageFormat countMsg;

    public AbstractCountCountMax(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.countMax");
        var msg2 = bundle.getString("fields.count");
        countMaxMsg = new MessageFormat(msg1, locale);
        countMsg = new MessageFormat(msg2, locale);
    }

    protected String turnCountAndCountMaxToText(int countMin, int countMax) {
        Map<String, Object> arguments = Map.of(
                "minCount", countMin,
                "maxCount", countMax
        );
        return countMaxMsg.format(arguments);
    }

    protected String turnCountToText(int count) {
        return countMsg.format(new Object[]{count});
    }
}
