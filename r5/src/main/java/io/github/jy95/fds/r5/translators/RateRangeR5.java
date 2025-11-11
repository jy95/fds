package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.RateRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RangeToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Range;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "doseAndRate.rateRange"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class RateRangeR5 implements RateRange<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var rateRange = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.RATE_RANGE
                );

        var rateRangeMsg = translationService.getMessage(KEY_RATE_RANGE);
        return RangeToStringR5
                .INSTANCE
                .convert(translationService, (Range) rateRange)
                .thenApplyAsync(rateRatioText -> rateRangeMsg.format(new Object[]{rateRatioText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return translationService
                .getConfig()
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRange);
    }
}
