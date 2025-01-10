package io.github.jy95.r4.translators;

import io.github.jy95.common.types.AbstractTranslator;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class ExtensionR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    public ExtensionR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .getFromExtensionsToString()
                .apply(
                        dosage.getExtension()
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasExtension();
    }
}
