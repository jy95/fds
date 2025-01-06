package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RatioToString;
import org.hl7.fhir.r4.model.Dosage;

import com.ibm.icu.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class MaxDosePerPeriod extends AbstractTranslator {

    // Translations
    private final MessageFormat maxDosePerPeriodMsg;

    public MaxDosePerPeriod(FDUConfig config) {
        super(config);
        String msg = getResources().getString("fields.maxDosePerPeriod");
        maxDosePerPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();
        var bundle = getResources();

        return RatioToString
                .convert(bundle, getConfig(), ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}