package io.github.jy95.r4.translators;

import io.github.jy95.common.functions.ListToString;
import io.github.jy95.common.translators.AbstractAsNeeded;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsNeededR4 extends AbstractAsNeeded<FDSConfigR4, Dosage> {

    public AsNeededR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();

        // Complex case - "as-need" for ...
        if (dosage.hasAsNeededCodeableConcept()) {
            var code = dosage.getAsNeededCodeableConcept();
            var codeAsText = this
                    .getConfig()
                    .getFromCodeableConceptToString()
                    .apply(code);

            return codeAsText
                    .thenApplyAsync(v -> ListToString.convert(bundle, List.of(v)))
                    .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
        }

        // Simple case - only "as-needed"
        return CompletableFuture.supplyAsync(() -> asNeededMsg);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAsNeeded();
    }
}
