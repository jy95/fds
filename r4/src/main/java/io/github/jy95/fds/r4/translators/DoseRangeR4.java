package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseRange"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class DoseRangeR4 implements Translator<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var doseRange = config
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_RANGE);

        return RangeToStringR4
                .getInstance()
                .convert(translationService, (Range) doseRange);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        var config = translationService.getConfig();
        return config
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseRange);
    }
}
