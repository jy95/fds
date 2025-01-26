package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.MaxDosePerAdministration;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "maxDosePerAdministration"
 *
 * @author jy95
 */
public class MaxDosePerAdministrationR5 implements MaxDosePerAdministration<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "maxDosePerAdministration" translation */
    protected final MessageFormat maxDosePerAdministrationMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code MaxDosePerAdministrationR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public MaxDosePerAdministrationR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.config = config;
        this.maxDosePerAdministrationMsg = getMaxDosePerAdministrationMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();

        return QuantityToStringR5
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
