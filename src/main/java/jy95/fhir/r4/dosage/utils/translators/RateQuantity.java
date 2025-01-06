package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.QuantityToString;
import jy95.fhir.r4.dosage.utils.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.concurrent.CompletableFuture;

public class RateQuantity extends AbstractTranslator {

    // Translations
    private final MessageFormat rateQuantityMsg;

    public RateQuantity(FDUConfig config) {
        super(config);
        var doseMsg = getResources().getString("fields.rateQuantity");
        rateQuantityMsg = new MessageFormat(doseMsg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var doseAndRate = dosage.getDoseAndRate();
        var rateQuantity = getConfig()
                .getSelectDosageAndRateField()
                .apply(doseAndRate, DoseAndRateKey.RATE_QUANTITY);
        return QuantityToString
                .convert(bundle, getConfig(), (Quantity) rateQuantity)
                .thenApplyAsync(rateQuantityText -> rateQuantityMsg.format(new Object[]{rateQuantityText}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(Dosage.DosageDoseAndRateComponent::hasRateQuantity);
    }

}
