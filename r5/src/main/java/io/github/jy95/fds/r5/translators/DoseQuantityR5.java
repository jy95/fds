package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.DoseQuantity;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "doseAndRate.doseQuantity"
 *
 * @author jy95
 */
public class DoseQuantityR5 implements DoseQuantity<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "doseQuantity" translation. */
    protected final MessageFormat doseQuantityMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DoseQuantityR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public DoseQuantityR5(FDSConfigR5 config, ResourceBundle bundle) {
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
        return QuantityToStringR5
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
