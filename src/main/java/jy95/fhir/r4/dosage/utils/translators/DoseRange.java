package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RangeToString;
import jy95.fhir.r4.dosage.utils.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

public class DoseRange extends AbstractTranslator {

    // Translations
    private final MessageFormat doseRangeMsg;

    public DoseRange(FDUConfig config) {
        super(config);
        var msg = getResources().getString("fields.doseRange");
        doseRangeMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var doseAndRate = dosage.getDoseAndRate();
        var doseRange = getConfig()
                .getSelectDosageAndRateField()
                .apply(doseAndRate, DoseAndRateKey.DOSE_RANGE);

        return RangeToString
                .convert(bundle, getConfig(), (Range) doseRange)
                .thenApplyAsync(rangeText -> doseRangeMsg.format(new Object[]{rangeText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(Dosage.DosageDoseAndRateComponent::hasDoseRange);
    }
}
