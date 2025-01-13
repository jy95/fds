package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractRateRatio;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RatioToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Ratio;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.rateRatio"
 *
 * @author jy95
 */
public class RateRatioR4 extends AbstractRateRatio<FDSConfigR4, Dosage> {

    private final RatioToStringR4 ratioToStringR4;

    /**
     * Constructor for {@code RateRatioR4}.
     *
     * @param config The configuration object used for translation.
     */
    public RateRatioR4(FDSConfigR4 config) {
        super(config);
        ratioToStringR4 = new RatioToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateRatio = getConfig()
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.RATE_RATIO);

        return ratioToStringR4
                .convert(getResources(), getConfig(), (Ratio) rateRatio)
                .thenApplyAsync(rateRatioText -> rateRatioMsg.format(new Object[]{rateRatioText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return getConfig()
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRatio);
    }
}
