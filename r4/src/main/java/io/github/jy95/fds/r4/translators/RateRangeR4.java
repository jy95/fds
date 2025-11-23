package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.dosage.RateRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.rateRange"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class RateRangeR4 implements RateRange<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

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

        return RangeToStringR4
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
