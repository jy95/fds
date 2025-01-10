package io.github.jy95.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.common.config.FDSConfig;
import io.github.jy95.common.functions.ListToString;
import io.github.jy95.common.types.AbstractTranslatorTiming;

import java.util.List;
import java.util.Map;

/**
 * An abstract class for translating "timing.repeat.dayOfWeek".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractDayOfWeek<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "dayOfWeek" translation. */
    protected final MessageFormat dayOfWeekMsg;

    /**
     * Constructor for {@code AbstractDayOfWeek}.
     * @param config The configuration object used for translation.
     */
    public AbstractDayOfWeek(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.dayOfWeek");
        dayOfWeekMsg = new MessageFormat(msg, locale);
    }

    /**
     * Translates a single day code into its corresponding day of the week in text.
     *
     * @param dayCode the code representing the day (e.g., "mon", "tue").
     * @return the translated day of the week as a string.
     */
    protected String dayToText(String dayCode) {
        String dayTranslation = getResources().getString("day." + dayCode);

        // Use ICU's MessageFormat to handle the translation with choice formatting
        MessageFormat messageFormat = new MessageFormat(dayTranslation, this.getConfig().getLocale());
        Map<String, Object> dayArguments = Map.of(
                "dayType", "long"
        );

        return messageFormat.format(dayArguments);
    }

    /**
     * Translates a list of days in text.
     * @param days the list of days (e.g., ["monday", "wednesday", "friday"]).
     * @return the translated days of the week as a formatted string.
     */
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
