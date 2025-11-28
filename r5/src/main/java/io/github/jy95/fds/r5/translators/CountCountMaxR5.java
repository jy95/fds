package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.timing.repeat.CountCountMax;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Timing.TimingRepeatComponent;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.count" / "timing.repeat.countMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class CountCountMaxR5 implements CountCountMax<TimingRepeatComponent> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(TimingRepeatComponent data) {
        return data.hasCount() || hasCountMax(data);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCountMax(TimingRepeatComponent data) {
        return data.hasCountMax();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(TimingRepeatComponent data) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: If there's a countMax, there must be a count
            if (hasCountMax(data)) {
                var countMaxMsg = translationService.getMessage(KEY_COUNT_MAX);
                Map<String, Object> arguments = Map.of(
                        "minCount", data.getCount(),
                        "maxCount", data.getCountMax()
                );
                return countMaxMsg.format(arguments);
            }

            var countMsg = translationService.getMessage(KEY_COUNT);
            return countMsg.format(new Object[]{
                    data.getCount()
            });

        });
    }
}
