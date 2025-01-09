package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractMaxDosePerAdministration;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class MaxDosePerAdministrationR4 extends AbstractMaxDosePerAdministration<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    public MaxDosePerAdministrationR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();
        var bundle = getResources();

        return quantityToStringR4
                .convert(bundle, getConfig(), quantity)
                .thenApplyAsync(
                        (quantityText) -> maxDosePerAdministrationMsg.format(new Object[] { quantityText })
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerAdministration();
    }
}
