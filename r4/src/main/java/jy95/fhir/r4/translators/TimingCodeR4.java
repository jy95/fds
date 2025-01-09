package jy95.fhir.r4.translators;

import jy95.fhir.common.types.AbstractTranslatorTiming;
import jy95.fhir.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class TimingCodeR4 extends AbstractTranslatorTiming<FDSConfigR4, Dosage> {

    public TimingCodeR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
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
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasCode();
    }
}
