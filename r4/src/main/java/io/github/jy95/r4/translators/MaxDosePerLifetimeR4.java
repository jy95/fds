package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractMaxDosePerLifetime;
import io.github.jy95.r4.config.FDSConfigR4;
import io.github.jy95.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class MaxDosePerLifetimeR4 extends AbstractMaxDosePerLifetime<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    public MaxDosePerLifetimeR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerLifetime();
        var bundle = getResources();

        return quantityToStringR4
                .convert(bundle, getConfig(), quantity)
                .thenApplyAsync((quantityText) -> maxDosePerLifetimeMsg.format(new Object[] { quantityText }));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerLifetime();
    }
}
