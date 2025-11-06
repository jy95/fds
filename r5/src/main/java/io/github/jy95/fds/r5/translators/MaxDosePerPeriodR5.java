package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.MaxDosePerPeriod;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RatioToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "maxDosePerPeriod"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class MaxDosePerPeriodR5 implements MaxDosePerPeriod<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var bundle = translationService.getBundle();

        var ratioFutures = dosage
                .getMaxDosePerPeriod()
                .stream()
                .map(ratio -> RatioToStringR5.getInstance().convert(bundle, config, ratio))
                .toList();

        var maxDosePerPeriodMsg = translationService.getMessage(KEY_MAX_DOSE_PER_PERIOD);

        return CompletableFuture
                .allOf(ratioFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var ratioTexts = ratioFutures
                            .stream()
                            .map(future -> future.getNow(""))
                            .toList();
                    return ListToString.convert(bundle, ratioTexts);
                })
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
