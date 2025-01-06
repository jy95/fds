package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;

import java.util.concurrent.CompletableFuture;

import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.RangeToString;

public class BoundsRange extends AbstractTranslator {

    // Translations
    private final MessageFormat boundsRangeMsg;

    public BoundsRange(FDUConfig config) {
        super(config);
        String msg = getResources().getString("fields.boundsRange");
        boundsRangeMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();
        var bundle = this.getResources();

        return RangeToString
                .convert(bundle, this.getConfig(), boundsRange)
                .thenApplyAsync(v -> boundsRangeMsg.format(new Object[]{v}));
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
