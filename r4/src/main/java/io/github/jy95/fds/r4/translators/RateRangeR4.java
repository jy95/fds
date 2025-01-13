package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractRateRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.rateRange"
 *
 * @author jy95
 */
public class RateRangeR4 extends AbstractRateRange<FDSConfigR4, Dosage> {

    private final RangeToStringR4 rangeToStringR4;

    /**
     * Constructor for {@code RateRangeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public RateRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateRange = getConfig()
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.RATE_RANGE);

        return rangeToStringR4
                .convert(getResources(), getConfig(), (Range) rateRange)
                .thenApplyAsync(rateRatioText -> rateRangeMsg.format(new Object[]{rateRatioText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return getConfig()
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRange);
    }
}
