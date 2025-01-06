package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class CountCountMax extends AbstractTranslator {

    public CountCountMax(FDUConfig config) {
        super(config);
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
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var countMin = repeat.getCount();
        var countMax = repeat.getCountMax();

        var countMsg = bundle.getString("fields.countMax");
        Map<String, Object> arguments = Map.of(
                "minCount", countMin,
                "maxCount", countMax
        );

        return new MessageFormat(countMsg, locale).format(arguments);
    }

    private String turnCountToText(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var count = repeat.getCount();

        var countMsg = bundle.getString("fields.count");
        return new MessageFormat(countMsg, locale).format(new Object[]{count});
    }
}
