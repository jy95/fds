package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.MaxDosePerAdministration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerAdministration"
 *
 * @author jy95
 */
public class MaxDosePerAdministrationR4 implements MaxDosePerAdministration<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "maxDosePerAdministration" translation */
    protected final MessageFormat maxDosePerAdministrationMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code MaxDosePerAdministrationR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MaxDosePerAdministrationR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.config = config;
        this.maxDosePerAdministrationMsg = getMaxDosePerAdministrationMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();

        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, quantity)
                .thenApplyAsync(
                        (quantityText) -> maxDosePerAdministrationMsg.format(new Object[] { quantityText })
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerAdministration();
    }
}
