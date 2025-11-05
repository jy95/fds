package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseQuantity"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class DoseQuantityR4 implements Translator<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var config = translationService.getConfig();
        var bundle = translationService.getBundle();
        var doseQuantity = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.DOSE_QUANTITY)
                ;
        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, (Quantity) doseQuantity);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        var config = translationService.getConfig();
        return config
                .hasMatchingComponent(
                        dosage,
                        Dosage.DosageDoseAndRateComponent::hasDoseQuantity
                );
    }
}
