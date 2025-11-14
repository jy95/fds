package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.AsNeeded;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "asNeededBoolean" / "asNeededCodeableConcept".
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class AsNeededR4 implements AsNeeded<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {

        // Complex case - "as-need" for ...
        if (hasCodeableConcepts(dosage)) {
            return convertCodeableConcepts(dosage);
        }

        var asNeededMsg = translationService.getText(KEY_AS_NEEDED);

        // Simple case - only "as-needed"
        return CompletableFuture.supplyAsync(() -> asNeededMsg);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAsNeeded();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCodeableConcepts(Dosage dosage) {
        return dosage.hasAsNeededCodeableConcept();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertCodeableConcepts(Dosage dosage) {
        var code = dosage.getAsNeededCodeableConcept();
        var config = translationService.getConfig();
        var asNeededForMsg = translationService.getMessage(KEY_AS_NEEDED_FOR);
        var codeAsText = config
                .fromCodeableConceptToString(code);

        return codeAsText
                .thenApplyAsync(v -> ListToString.convert(translationService, List.of(v)))
                .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
    }
}
