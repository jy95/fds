package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.DayOfWeekFormatter;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.DayOfWeek;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.dayOfWeek"
 *
 * @author jy95
 */
public class DayOfWeekR4 implements DayOfWeek<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /**
     * Day of week formatter
     */
    private final DayOfWeekFormatter dayOfWeekFormatter;

    /**
     * Constructor for {@code DayOfWeekR4}.
     * @param translationService the translation service
     */
    public DayOfWeekR4(TranslationService<FDSConfigR4> translationService) {
        this.translationService = translationService;
        this.dayOfWeekFormatter = new DayOfWeekFormatter(translationService.getConfig().getLocale());
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
        var dayOfWeeksAsString = ListToString.convert(translationService, days);
        var dayOfWeekMsg = translationService.getMessage(KEY_DAY_OF_WEEK);

        Map<String, Object> messageArguments = Map.of(
                "dayCondition", days.size(),
                "day", dayOfWeeksAsString
        );

        // Use ICU MessageFormat for plural and select formatting
        return dayOfWeekMsg.format(messageArguments);
    }
}
