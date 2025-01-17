package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.MaxDosePerLifetime;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerLifetime"
 *
 * @author jy95
 */
public class MaxDosePerLifetimeR4 implements MaxDosePerLifetime<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "maxDosePerLifetime" translation */
    protected final MessageFormat maxDosePerLifetimeMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code MaxDosePerLifetimeR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public MaxDosePerLifetimeR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.config = config;
        this.maxDosePerLifetimeMsg = getMaxDosePerLifetimeMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerLifetime();

        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, quantity)
                .thenApplyAsync((quantityText) -> maxDosePerLifetimeMsg.format(new Object[] { quantityText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerLifetime();
    }
}
