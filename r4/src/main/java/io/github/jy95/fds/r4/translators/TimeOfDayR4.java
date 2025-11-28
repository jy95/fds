package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.TimeOfDay;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;
import org.hl7.fhir.r4.model.PrimitiveType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.timeOfDay"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimeOfDayR4 implements TimeOfDay<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public List<String> getTimes(TimingRepeatComponent data) {
        return data
                .getTimeOfDay()
                .stream()
                .map(PrimitiveType::getValue)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        return data.hasTimeOfDay();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent data) {
        return CompletableFuture.supplyAsync(() -> {

            var times = getTimes(data);
            var timeOfDays = times.stream().map(this::formatString).toList();
            var timeOfDayMsg = translationService.getMessage(KEY_TIME_OF_DAY);
            var timeOfDaysAsString = ListToString.convert(translationService, timeOfDays);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", timeOfDays.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }
}
