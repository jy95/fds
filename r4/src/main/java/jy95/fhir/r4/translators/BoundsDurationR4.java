package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractBoundsDuration;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class BoundsDurationR4 extends AbstractBoundsDuration<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    public BoundsDurationR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsDuration();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var boundsDuration = dosage.getTiming().getRepeat().getBoundsDuration();
        return quantityToStringR4
                .convert(bundle, getConfig(), boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
