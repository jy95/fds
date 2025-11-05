package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.MaxDosePerPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RatioToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerPeriod"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class MaxDosePerPeriodR4 implements MaxDosePerPeriod<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var config = translationService.getConfig();
        var bundle = translationService.getBundle();
        var maxDosePerPeriodMsg = translationService.getMessage(KEY_MAX_DOSE_PER_PERIOD);

        return RatioToStringR4
                .getInstance()
                .convert(bundle, config, ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
