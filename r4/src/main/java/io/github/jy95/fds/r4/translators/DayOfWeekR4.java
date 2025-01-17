package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.DayOfWeek;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.dayOfWeek"
 *
 * @author jy95
 */
public class DayOfWeekR4 implements DayOfWeek<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "dayOfWeek" translation. */
    protected final MessageFormat dayOfWeekMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DayOfWeekR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public DayOfWeekR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.dayOfWeekMsg = getDayOfWeekMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasDayOfWeek();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = dosage.getTiming().getRepeat().getDayOfWeek();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> {
                        String dayCode = day.getCode().toLowerCase(); // Get the lowercase day code
                        return dayToText(dayCode);
                    })
                    .toList();

            return daysToText(dayOfWeeksCodes);
        });
    }

    /**
     * Translates a single day code into its corresponding day of the week in text.
     *
     * @param dayCode the code representing the day (e.g., "mon", "tue").
     * @return the translated day of the week as a string.
     */
    private String dayToText(String dayCode) {
        String dayTranslation = bundle.getString("day." + dayCode);

        // Use ICU's MessageFormat to handle the translation with choice formatting
        MessageFormat messageFormat = new MessageFormat(dayTranslation, config.getLocale());
        Map<String, Object> dayArguments = Map.of(
                "dayType", "long"
        );

        return messageFormat.format(dayArguments);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /**
     * Translates a list of days in text.
     *
     * @param days the list of days (e.g., ["monday", "wednesday", "friday"]).
     * @return the translated days of the week as a formatted string.
     */
    private String daysToText(List<String> days) {
        var dayOfWeeksAsString = ListToString.convert(bundle, days);

        Map<String, Object> messageArguments = Map.of(
                "dayCondition", days.size(),
                "day", dayOfWeeksAsString
        );

        // Use ICU MessageFormat for plural and select formatting
        return dayOfWeekMsg.format(messageArguments);
    }
}
