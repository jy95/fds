package jy95.fhir.r4.translators;

import jy95.fhir.common.types.AbstractTranslator;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class ModifierExtensionR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    public ModifierExtensionR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .getFromExtensionsToString()
                .apply(
                        dosage.getModifierExtension()
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasModifierExtension();
    }
}