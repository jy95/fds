package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.DayOfWeekFormatter;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.DayOfWeek;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.dayOfWeek"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class DayOfWeekR5 implements DayOfWeek<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        return data.hasDayOfWeek();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent data) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = data.getDayOfWeek();
            var locale = translationService.getConfig().getLocale();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> DayOfWeekFormatter
                            .codeToLongText(
                                day.getCode().toLowerCase(),
                                locale
                            )
                    )
                    .toList();

            return daysToText(dayOfWeeksCodes);
        });
    }

    /**
     * Translates a list of days in text.
     *
     * @param days the list of days (e.g., ["monday", "wednesday", "friday"]).
     * @return the translated days of the week as a formatted string.
     */
    private String daysToText(List<String> days) {
        var dayOfWeeksAsString = ListToString.convert(translationService, days);

        Map<String, Object> messageArguments = Map.of(
                "dayCondition", days.size(),
                "day", dayOfWeeksAsString
        );

        // Use ICU MessageFormat for plural and select formatting
        var dayOfWeekMsg = translationService.getMessage(KEY_DAY_OF_WEEK);
        return dayOfWeekMsg.format(messageArguments);
    }
}
