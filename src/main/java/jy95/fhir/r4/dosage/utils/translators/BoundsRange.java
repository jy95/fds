package jy95.fhir.r4.dosage.utils.translators;


import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RangeToString;

public class BoundsRange extends AbstractTranslator {

    public BoundsRange(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();
        var bundle = this.getResources();
        return RangeToString
                .convert(bundle, this.getConfig(), boundsRange)
                .thenApplyAsync(v -> {
                    String msg = bundle.getString("fields.boundsRange");
                    return MessageFormat.format(msg, v);
                });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
