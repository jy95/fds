package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractMaxDosePerAdministration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerAdministration"
 *
 * @author jy95
 */
public class MaxDosePerAdministrationR4 extends AbstractMaxDosePerAdministration<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code MaxDosePerAdministrationR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MaxDosePerAdministrationR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();
        var bundle = getResources();

        return QuantityToStringR4
                .getInstance()
                .convert(bundle, getConfig(), quantity)
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
