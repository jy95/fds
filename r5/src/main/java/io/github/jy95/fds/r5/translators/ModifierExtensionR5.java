package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.ModifierExtension;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "modifierExtension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class ModifierExtensionR5 implements ModifierExtension<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return translationService
                .getConfig()
                .fromExtensionsToString(
                        dosage.getModifierExtension()
                );
    }

}
