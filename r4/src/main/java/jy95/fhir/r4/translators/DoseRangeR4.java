package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractDoseRange;
import jy95.fhir.common.types.DoseAndRateKey;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

import static jy95.fhir.r4.config.DefaultImplementationsR4.hasMatchingComponent;

public class DoseRangeR4 extends AbstractDoseRange<FDSConfigR4, Dosage> {

    protected final RangeToStringR4 rangeToStringR4;

    public DoseRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseRange = getConfig()
                .getSelectDosageAndRateField()
                .apply(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_RANGE);

        return rangeToStringR4
                .convert(getResources(), getConfig(), (Range) doseRange)
                .thenApplyAsync(rangeText -> doseRangeMsg.format(new Object[]{rangeText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseRange);
    }
}
