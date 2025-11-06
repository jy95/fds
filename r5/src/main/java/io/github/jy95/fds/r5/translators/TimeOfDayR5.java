package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.TimeOfDay;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.PrimitiveType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.timeOfDay"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimeOfDayR5 implements TimeOfDay<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public List<String> getTimes(Dosage dosage) {
        return dosage
                .getTiming()
                .getRepeat()
                .getTimeOfDay()
                .stream()
                .map(PrimitiveType::getValue)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && timing.getRepeat().hasTimeOfDay();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = translationService.getBundle();
        var timeOfDayMsg = translationService.getMessage(KEY_TIME_OF_DAY);

        return CompletableFuture.supplyAsync(() -> {

            var times = getTimes(dosage);
            var timeOfDays = times.stream().map(this::formatString).toList();
            var timeOfDaysAsString = ListToString.convert(bundle, timeOfDays);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", timeOfDays.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }
}
