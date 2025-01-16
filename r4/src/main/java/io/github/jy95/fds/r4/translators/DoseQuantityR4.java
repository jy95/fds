package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractDoseQuantity;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseQuantity"
 *
 * @author jy95
 */
public class DoseQuantityR4 extends AbstractDoseQuantity<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code DoseQuantityR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DoseQuantityR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseQuantity = getConfig()
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_QUANTITY);
        return QuantityToStringR4
                .getInstance()
                .convert(getResources(), getConfig(), (Quantity) doseQuantity)
                .thenApplyAsync(quantityText -> doseQuantityMsg.format(new Object[]{quantityText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return getConfig()
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseQuantity);
    }
}
