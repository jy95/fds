package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.QuantityToString;
import org.hl7.fhir.r4.model.Dosage;

import com.ibm.icu.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class MaxDosePerAdministration extends AbstractTranslator {

    // Translations
    private final MessageFormat maxDosePerAdministrationMsg;

    public MaxDosePerAdministration(FDUConfig config) {
        super(config);
        String msg = getResources().getString("fields.maxDosePerAdministration");
        maxDosePerAdministrationMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerAdministration();
        var bundle = getResources();

        return QuantityToString
                .convert(bundle, getConfig(), quantity)
                .thenApplyAsync(
                        (quantityText) -> maxDosePerAdministrationMsg.format(new Object[] { quantityText })
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerAdministration();
    }
}