package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.RateQuantity;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "doseAndRate.rateQuantity"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class RateQuantityR5 implements RateQuantity<FDSConfigR5, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var bundle = translationService.getBundle();
        var rateQuantity = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.RATE_QUANTITY
                );

        var rateQuantityMsg = translationService.getMessage(KEY_RATE_QUANTITY);
        return QuantityToStringR5
                .getInstance()
                .convert(bundle, config, (Quantity) rateQuantity)
                .thenApplyAsync(rateQuantityText -> rateQuantityMsg.format(new Object[]{rateQuantityText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return translationService
                .getConfig()
                .hasMatchingComponent(
                        dosage,
                        Dosage.DosageDoseAndRateComponent::hasRateQuantity
                );
    }
}
