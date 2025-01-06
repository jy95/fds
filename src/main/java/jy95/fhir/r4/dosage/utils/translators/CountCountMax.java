package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CountCountMax extends AbstractTranslator {

    // Translations
    private final MessageFormat countMaxMsg;
    private final MessageFormat countMsg;

    public CountCountMax(FDUConfig config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.countMax");
        var msg2 = bundle.getString("fields.count");
        countMaxMsg = new MessageFormat(msg1, locale);
        countMsg = new MessageFormat(msg2, locale);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var repeat = dosage.getTiming().getRepeat();

            // Rule: If there's a countMax, there must be a count
            if (repeat.hasCountMax()) {
                return turnCountAndCountMaxToText(dosage);
            }
            return turnCountToText(dosage);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasCount() ||
                dosage.getTiming().getRepeat().hasCountMax());
    }

    private String turnCountAndCountMaxToText(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var countMin = repeat.getCount();
        var countMax = repeat.getCountMax();

        Map<String, Object> arguments = Map.of(
                "minCount", countMin,
                "maxCount", countMax
        );

        return countMaxMsg.format(arguments);
    }

    private String turnCountToText(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var count = repeat.getCount();

        return countMsg.format(new Object[]{count});
    }
}
