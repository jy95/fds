package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.AbstractAsNeeded;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsNeededR4 extends AbstractAsNeeded<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code AsNeededR4}.
     * @param config The configuration object used for translation.
     */
    public AsNeededR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAsNeeded();
    }

    @Override
    protected boolean hasCodeableConcepts(Dosage dosage) {
        return dosage.hasAsNeededCodeableConcept();
    }

    @Override
    protected CompletableFuture<String> convertCodeableConcepts(Dosage dosage) {
        var bundle = getResources();
        var code = dosage.getAsNeededCodeableConcept();
        var codeAsText = this
                .getConfig()
                .getFromCodeableConceptToString()
                .apply(code);

        return codeAsText
                .thenApplyAsync(v -> ListToString.convert(bundle, List.of(v)))
                .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
    }
}
