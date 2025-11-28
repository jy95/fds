package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.translators.dosage.AsNeeded;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "asNeededBoolean" / "asNeededCodeableConcept".
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class AsNeededR5 implements AsNeeded<Dosage> {

    /** Translation service */
    private final TranslationService<FDSConfigR5> translationService;

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
        return dosage.hasAsNeeded() || hasCodeableConcepts(dosage);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCodeableConcepts(Dosage dosage) {
        return dosage.hasAsNeededFor();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertCodeableConcepts(Dosage dosage) {
        var codes = dosage.getAsNeededFor();
        var config = translationService.getConfig();

        var codesFutures = codes
                .stream()
                .map(config::fromCodeableConceptToString)
                .toList();

        var asNeededForMsg = translationService.getMessage(KEY_AS_NEEDED_FOR);

        return CompletableFuture
                .allOf(codesFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var codesAsText = codesFutures
                            .stream()
                            .map(future -> future.getNow(""))
                            .toList();

                    return ListToString.convert(translationService, codesAsText);
                })
                .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
    }
}
