package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RangeToString;
import jy95.fhir.r4.dosage.utils.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

public class RateRange extends AbstractTranslator {

    // Translations
    private final MessageFormat rateRangeMsg;

    public RateRange(FDUConfig config) {
        super(config);
        var msg1 = getResources().getString("fields.rateRange");
        rateRangeMsg = new MessageFormat(msg1, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var doseAndRate = dosage.getDoseAndRate();
        var rateRange = getConfig()
                .getSelectDosageAndRateField()
                .apply(doseAndRate, DoseAndRateKey.RATE_RANGE);

        return RangeToString
                .convert(bundle, getConfig(), (Range) rateRange)
                .thenApplyAsync(rateRatioText -> rateRangeMsg.format(new Object[]{rateRatioText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(Dosage.DosageDoseAndRateComponent::hasRateRange);
    }
}
