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
     * Instance to translate ratio to string
     */
    protected final RatioToStringR4 ratioToStringR4;

    /**
     * Constructor for {@code MaxDosePerPeriodR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MaxDosePerPeriodR4(FDSConfigR4 config) {
        super(config);
        ratioToStringR4 = new RatioToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var bundle = getResources();

        return ratioToStringR4
                .convert(bundle, getConfig(), ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
