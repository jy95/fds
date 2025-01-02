package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;

import java.util.concurrent.CompletableFuture;

public class TimingCode extends AbstractTranslator {

    public TimingCode(FDUConfig config){
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .getFromCodeableConceptToString()
                .apply(
                        dosage.getTiming().getCode()
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasCode();
    }

}
