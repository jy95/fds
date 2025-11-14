package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.MaxDosePerAdministration;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "maxDosePerAdministration"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class MaxDosePerAdministrationR5 implements MaxDosePerAdministration<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();
        var maxDosePerAdministrationMsg = translationService.getMessage(KEY_MAX_DOSE_PER_ADMINISTRATION);

        return QuantityToStringR5
                .INSTANCE
                .convert(translationService, quantity)
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
