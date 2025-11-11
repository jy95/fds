package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.RateRatio;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RatioToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Ratio;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "doseAndRate.rateRatio"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class RateRatioR5 implements RateRatio<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var rateRatio = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.RATE_RATIO
                );

        var rateRatioMsg = translationService.getMessage(KEY_RATE_RATIO);

        return RatioToStringR5
                .getInstance()
                .convert(translationService, (Ratio) rateRatio)
                .thenApplyAsync(rateRatioText -> rateRatioMsg.format(new Object[]{rateRatioText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return translationService
                .getConfig()
                .hasMatchingComponent(
                        dosage,
                        Dosage.DosageDoseAndRateComponent::hasRateRatio
                );
    }
}
