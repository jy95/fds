package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;

import java.util.concurrent.CompletableFuture;

public class Method extends AbstractTranslator {

    public Method(FDUConfig config){
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var code = dosage.getMethod();
        var fct = this.getConfig().getFromCodeableConceptToString();
        return fct.apply(code);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMethod();
    }
}
