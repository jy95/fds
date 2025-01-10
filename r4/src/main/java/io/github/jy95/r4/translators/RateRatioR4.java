package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractRateRatio;
import io.github.jy95.common.types.DoseAndRateKey;
import io.github.jy95.r4.config.FDSConfigR4;
import io.github.jy95.r4.functions.RatioToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Ratio;

import java.util.concurrent.CompletableFuture;

import static io.github.jy95.r4.config.DefaultImplementationsR4.hasMatchingComponent;

public class RateRatioR4 extends AbstractRateRatio<FDSConfigR4, Dosage> {

    private final RatioToStringR4 ratioToStringR4;

    public RateRatioR4(FDSConfigR4 config) {
        super(config);
        ratioToStringR4 = new RatioToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateRatio = getConfig()
                .getSelectDosageAndRateField()
                .apply(dosage.getDoseAndRate(), DoseAndRateKey.RATE_RATIO);

        return ratioToStringR4
                .convert(getResources(), getConfig(), (Ratio) rateRatio)
                .thenApplyAsync(rateRatioText -> rateRatioMsg.format(new Object[]{rateRatioText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRatio);
    }
}
