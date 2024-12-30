package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;

import java.util.concurrent.CompletableFuture;

public class ModifierExtension extends AbstractTranslator {

    public ModifierExtension(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var extensions = dosage.getModifierExtension();
        var fct = this.getConfig().getFromExtensionsToString();
        return fct.apply(extensions);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasModifierExtension();
    }
}
