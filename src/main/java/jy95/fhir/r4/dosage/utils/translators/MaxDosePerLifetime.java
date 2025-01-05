package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.QuantityToString;
import org.hl7.fhir.r4.model.Dosage;

import com.ibm.icu.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

public class MaxDosePerLifetime extends AbstractTranslator {

    public MaxDosePerLifetime(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var quantity = dosage.getMaxDosePerLifetime();
        var bundle = getResources();

        return QuantityToString
                .convert(bundle, getConfig(), quantity)
                .thenApplyAsync((quantityText) -> {
                    String msg = bundle.getString("fields.maxDosePerLifetime");
                    return new MessageFormat(msg, getConfig().getLocale()).format(new Object[] { quantityText });
                });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerLifetime();
    }
}