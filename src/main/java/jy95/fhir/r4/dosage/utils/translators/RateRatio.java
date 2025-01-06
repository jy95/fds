package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RatioToString;
import jy95.fhir.r4.dosage.utils.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Ratio;

import java.util.concurrent.CompletableFuture;

public class RateRatio extends AbstractTranslator {

    // Translations
    private final MessageFormat rateRatioMsg;

    public RateRatio(FDUConfig config) {
        super(config);
        var msg = getResources().getString("fields.rateRatio");
        rateRatioMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var doseAndRate = dosage.getDoseAndRate();
        var rateRatio = getConfig()
                .getSelectDosageAndRateField()
                .apply(doseAndRate, DoseAndRateKey.RATE_RATIO);

        return RatioToString
                .convert(bundle, getConfig(), (Ratio) rateRatio)
                .thenApplyAsync(rateRatioText -> rateRatioMsg.format(new Object[]{rateRatioText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(Dosage.DosageDoseAndRateComponent::hasRateRatio);
    }
}
