package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.types.AbstractTranslator;

import java.util.List;
import java.util.Map;

public abstract class AbstractDayOfWeek<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    protected final MessageFormat dayOfWeekMsg;

    public AbstractDayOfWeek(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.dayOfWeek");
        dayOfWeekMsg = new MessageFormat(msg, locale);
    }

    protected String dayToText(String dayCode) {
        String dayTranslation = getResources().getString("day." + dayCode);

        // Use ICU's MessageFormat to handle the translation with choice formatting
        MessageFormat messageFormat = new MessageFormat(dayTranslation, this.getConfig().getLocale());
        Map<String, Object> dayArguments = Map.of(
                "dayType", "long"
        );

        return messageFormat.format(dayArguments);
    }

    protected String daysToText(List<String> days) {
        var dayOfWeeksAsString = ListToString.convert(getResources(), days);

        Map<String, Object> messageArguments = Map.of(
                "dayCondition", days.size(),
                "day", dayOfWeeksAsString
        );

        // Use ICU MessageFormat for plural and select formatting
        return dayOfWeekMsg.format(messageArguments);
    }
}
