package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.MaxDosePerAdministration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerAdministration"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class MaxDosePerAdministrationR4 implements MaxDosePerAdministration<FDSConfigR4, Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();
        var config = translationService.getConfig();
        var bundle = translationService.getBundle();
        var maxDosePerAdministrationMsg = translationService.getMessage(KEY_MAX_DOSE_PER_ADMINISTRATION);

        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, quantity)
                .thenApplyAsync(
                        (quantityText) -> maxDosePerAdministrationMsg.format(new Object[] { quantityText })
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerAdministration();
    }
}
