package io.github.jy95.common.translators;

import io.github.jy95.common.config.FDSConfig;
import io.github.jy95.common.functions.ListToString;
import io.github.jy95.common.types.AbstractTranslator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "additionalInstruction".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractAdditionalInstruction<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    /**
     * Constructor for {@code AbstractAdditionalInstruction}.
     * @param config The configuration object used for translation.
     */
    public AbstractAdditionalInstruction(C config) {
        super(config);
    }

    /**
     * Processes a list of asynchronous additional instructions and converts them to a single string.
     *
     * <p>This method waits for all the provided CompletableFuture instances to complete,
     * collects their results as strings, and then uses {@link ListToString#convert}
     * to combine them into a single string representation.</p>
     *
     * @param additionalInstructions A list of {@link CompletableFuture} objects representing
     *                               the additional instructions to be processed.
     * @return A {@link CompletableFuture} that, when completed, returns a string
     *         representing all the additional instructions combined.
     */
    protected CompletableFuture<String> instructionsFuture(
            List<CompletableFuture<String>> additionalInstructions
    ) {
        return CompletableFuture
                .allOf(additionalInstructions.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var additionalInstructionsAsText = additionalInstructions
                            .stream()
                            .map(future -> future.getNow(""))
                            .toList();

                    // Use ListToString.convert with the translators' resources
                    var bundle = this.getResources();
                    return ListToString.convert(bundle, additionalInstructionsAsText);
                });
    }
}
