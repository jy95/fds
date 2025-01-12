package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractRateQuantity;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

import static io.github.jy95.fds.r4.config.DefaultImplementationsR4.hasMatchingComponent;

/**
 * R4 class for translating "doseAndRate.rateQuantity"
 */
public class RateQuantityR4 extends AbstractRateQuantity<FDSConfigR4, Dosage> {

    private final QuantityToStringR4 quantityToStringR4;

    /**
     * Constructor for {@code RateQuantityR4}.
     * @param config The configuration object used for translation.
     */
    public RateQuantityR4(FDSConfigR4 config) {
        super(config);
        quantityToStringR4 = new QuantityToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateQuantity = getConfig()
                .getSelectDosageAndRateField()
                .apply(dosage.getDoseAndRate(), DoseAndRateKey.RATE_QUANTITY);
        return quantityToStringR4
                .convert(getResources(), getConfig(), (Quantity) rateQuantity)
                .thenApplyAsync(rateQuantityText -> rateQuantityMsg.format(new Object[]{rateQuantityText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateQuantity);
    }
}
