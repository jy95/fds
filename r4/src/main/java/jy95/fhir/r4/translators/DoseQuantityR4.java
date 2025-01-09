package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractDoseQuantity;
import jy95.fhir.common.types.DoseAndRateKey;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

import static jy95.fhir.r4.config.DefaultImplementationsR4.hasMatchingComponent;

public class DoseQuantityR4 extends AbstractDoseQuantity<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    public DoseQuantityR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseQuantity = getConfig()
                .getSelectDosageAndRateField()
                .apply(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_QUANTITY);
        return quantityToStringR4
                .convert(getResources(), getConfig(), (Quantity) doseQuantity)
                .thenApplyAsync(quantityText -> doseQuantityMsg.format(new Object[]{quantityText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseQuantity);
    }
}
