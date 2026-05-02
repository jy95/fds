package io.github.jy95.fds.common.translators.dosage;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.CodeableConceptProcessor;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

/**
 * Translator for "additionalInstruction" field.
 *
 * @param <D> The type of the translated data.
 * @param <U> The type of the additional instruction.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class AdditionalInstruction<
D, 
U extends IBase,
C extends FDSConfig & CodeableConceptProcessor<U> 
> implements Translator<D> {

    /** Translation service for obtaining configuration and resources. */
    private final TranslationService<C> translationService;
    /** Extractor for obtaining additional instructions from the translated data. */
    private final Function<D, List<U>> extractor;
    /** Predicate to check the presence of additional instructions in the translated data. */
    private final Predicate<D> presence; 

    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }

    @Override
    public CompletableFuture<String> convert(D data) {

        var additionalInstructions = extractor
                .apply(data)
                .stream()
                .map(translationService.getConfig()::fromCodeableConceptToString)
                .toList();

        return instructionsFuture(additionalInstructions);
    }

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
     * @param additionalInstructions A list of
     *                               {@link java.util.concurrent.CompletableFuture}
     *                               objects representing
     *                               the additional instructions to be processed.
     * @return A {@link java.util.concurrent.CompletableFuture} that, when
     *         completed, returns a string
     *         representing all the additional instructions combined.
     */
    private CompletableFuture<String> instructionsFuture(List<CompletableFuture<String>> additionalInstructions) {
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
