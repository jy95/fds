package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractRateRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

import static io.github.jy95.fds.r4.config.DefaultImplementationsR4.hasMatchingComponent;

public class RateRangeR4 extends AbstractRateRange<FDSConfigR4, Dosage> {

    private final RangeToStringR4 rangeToStringR4;

    /**
     * Constructor for {@code RateRangeR4}.
     * @param config The configuration object used for translation.
     */
    public RateRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateRange = getConfig()
                .getSelectDosageAndRateField()
                .apply(dosage.getDoseAndRate(), DoseAndRateKey.RATE_RANGE);

        return rangeToStringR4
                .convert(getResources(), getConfig(), (Range) rateRange)
                .thenApplyAsync(rateRatioText -> rateRangeMsg.format(new Object[]{rateRatioText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRange);
    }
}
