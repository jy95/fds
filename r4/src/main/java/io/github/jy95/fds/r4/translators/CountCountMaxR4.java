package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.CountCountMax;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.count" / "timing.repeat.countMax"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class CountCountMaxR4 implements CountCountMax<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasCount()
                || hasCountMax(dosage));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCountMax(Dosage dosage) {
        return dosage.getTiming().getRepeat().hasCountMax();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: If there's a countMax, there must be a count
            if (hasCountMax(dosage)) {
                Map<String, Object> arguments = Map.of(
                        "minCount", dosage.getTiming().getRepeat().getCount(),
                        "maxCount", dosage.getTiming().getRepeat().getCountMax()
                );
                var countMaxMsg = translationService.getMessage(KEY_COUNT_MAX);
                return countMaxMsg.format(arguments);
            }

            var countMsg = translationService.getMessage(KEY_COUNT);
            return countMsg.format(new Object[]{
                    dosage.getTiming().getRepeat().getCount()
            });

        });
    }
}
