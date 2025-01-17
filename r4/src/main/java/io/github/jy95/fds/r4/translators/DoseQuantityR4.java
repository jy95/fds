package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.DoseQuantity;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseQuantity"
 *
 * @author jy95
 */
public class DoseQuantityR4 implements DoseQuantity<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "doseQuantity" translation. */
    protected final MessageFormat doseQuantityMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DoseQuantityR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DoseQuantityR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.doseQuantityMsg = getDoseQuantityMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseQuantity = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.DOSE_QUANTITY)
                ;
        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, (Quantity) doseQuantity)
                .thenApplyAsync(quantityText -> doseQuantityMsg.format(new Object[]{quantityText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return config
                .hasMatchingComponent(
                        dosage,
                        Dosage.DosageDoseAndRateComponent::hasDoseQuantity
                );
    }
}
