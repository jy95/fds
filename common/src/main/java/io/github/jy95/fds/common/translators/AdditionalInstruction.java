package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * An interface for translating "additionalInstruction".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface AdditionalInstruction<D> extends Translator<D> {

    /**
     * Processes a list of asynchronous additional instructions and converts them to
     * a single string.
     *
     * <p>
     * This method waits for all the provided CompletableFuture instances to
     * complete,
     * collects their results as strings, and then uses
     * {@link io.github.jy95.fds.common.functions.ListToString#convert}
     * to combine them into a single string representation.
     * </p>
     *
     * @param translationService     The translation service used for obtaining
     *                               configuration and resources.
     * @param additionalInstructions A list of
     *                               {@link java.util.concurrent.CompletableFuture}
     *                               objects representing
     *                               the additional instructions to be processed.
     * @return A {@link java.util.concurrent.CompletableFuture} that, when
     *         completed, returns a string
     *         representing all the additional instructions combined.
     */
    default CompletableFuture<String> instructionsFuture(
            TranslationService<?> translationService,
            List<CompletableFuture<String>> additionalInstructions) {
        return CompletableFuture
                .allOf(additionalInstructions.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var additionalInstructionsAsText = additionalInstructions
                            .stream()
                            .map(future -> future.getNow(""))
                            .toList();

                    // Use ListToString.convert with the translators' resources
                    return ListToString.convert(translationService, additionalInstructionsAsText);
                });
    }
}
