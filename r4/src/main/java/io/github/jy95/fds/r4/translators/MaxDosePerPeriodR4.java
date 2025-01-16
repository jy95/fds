package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractMaxDosePerPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RatioToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerPeriod"
 *
 * @author jy95
 */
public class MaxDosePerPeriodR4 extends AbstractMaxDosePerPeriod<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code MaxDosePerPeriodR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MaxDosePerPeriodR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var bundle = getResources();

        return RatioToStringR4
                .getInstance()
                .convert(bundle, getConfig(), ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
