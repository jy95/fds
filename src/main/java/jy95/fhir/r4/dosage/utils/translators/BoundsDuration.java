package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.QuantityToString;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;
import com.ibm.icu.text.MessageFormat;

public class BoundsDuration extends AbstractTranslator {

    public BoundsDuration(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();
        var boundsDuration = dosage.getTiming().getRepeat().getBoundsDuration();
        return QuantityToString
                .convert(bundle, getConfig(), boundsDuration)
                .thenApplyAsync((durationText) -> {
                    String msg = bundle.getString("fields.boundsDuration");
                    return new MessageFormat(msg, getConfig().getLocale()).format(new Object[]{durationText});
                });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasBoundsDuration();
    }
}
