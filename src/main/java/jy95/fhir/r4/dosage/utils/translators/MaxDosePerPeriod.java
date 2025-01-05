package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RatioToString;
import org.hl7.fhir.r4.model.Dosage;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class MaxDosePerPeriod extends AbstractTranslator {

    public MaxDosePerPeriod(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var bundle = getResources();

        return RatioToString
                .convert(bundle, getConfig(), ratio)
                .thenApplyAsync((ratioText) -> {
                    String msg = bundle.getString("fields.maxDosePerPeriod");
                    return new MessageFormat(msg, getConfig().getLocale()).format(new Object[] {ratioText});
                });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
