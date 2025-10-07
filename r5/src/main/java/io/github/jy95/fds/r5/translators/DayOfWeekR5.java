package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.DayOfWeekFormatter;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.DayOfWeek;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.dayOfWeek"
 *
 * @author jy95
 */
public class DayOfWeekR5 implements DayOfWeek<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "dayOfWeek" translation. */
    protected final MessageFormat dayOfWeekMsg;

    /**
     * Day of week formatter
     */
    private final DayOfWeekFormatter dayOfWeekFormatter;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DayOfWeekR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public DayOfWeekR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.dayOfWeekMsg = getDayOfWeekMsg(bundle, config.getLocale());
        this.dayOfWeekFormatter = new DayOfWeekFormatter(config.getLocale());
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
                    .map(day -> dayOfWeekFormatter
                            .codeToLongText(
                                    day.getCode().toLowerCase()
                            )
                    )
                    .toList();

            return daysToText(dayOfWeeksCodes);
        });
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
