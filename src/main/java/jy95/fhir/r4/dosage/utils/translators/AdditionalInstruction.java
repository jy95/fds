package jy95.fhir.r4.dosage.utils.translators;

import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class AdditionalInstruction extends AbstractTranslator {

    public AdditionalInstruction(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var additionalInstructions = dosage
                .getAdditionalInstruction()
                .stream()
                .map(this.getConfig().getFromCodeableConceptToString())
                .toList();

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

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAdditionalInstruction();
    }
}
