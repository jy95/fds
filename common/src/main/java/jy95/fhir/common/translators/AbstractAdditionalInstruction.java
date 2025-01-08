package jy95.fhir.common.translators;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.types.AbstractTranslator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractAdditionalInstruction<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    public AbstractAdditionalInstruction(C config) {
        super(config);
    }

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
