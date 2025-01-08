package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractMaxDosePerPeriod;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.RatioToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class MaxDosePerPeriodR4 extends AbstractMaxDosePerPeriod<FDSConfigR4, Dosage> {

    protected final RatioToStringR4 ratioToStringR4;

    public MaxDosePerPeriodR4(FDSConfigR4 config) {
        super(config);
        ratioToStringR4 = new RatioToStringR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var bundle = getResources();

        return ratioToStringR4
                .convert(bundle, getConfig(), ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
