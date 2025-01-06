package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PeriodPeriodMax extends AbstractTranslator {

    // Translations
    private final MessageFormat periodMaxMsg;
    private final MessageFormat periodMsg;

    public PeriodPeriodMax(FDUConfig config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.periodMax");
        var msg2 = bundle.getString("fields.period");
        periodMaxMsg = new MessageFormat(msg1, locale);
        periodMsg = new MessageFormat(msg2, locale);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var repeat = dosage.getTiming().getRepeat();

            // Rule: if there's a period, there needs to be period units
            // Rule: period SHALL be a non-negative value
            // Rule: If there's a periodMax, there must be a period
            var hasPeriod = repeat.hasPeriod();
            var hasPeriodMax = repeat.hasPeriodMax();
            var hasBoth = hasPeriod && hasPeriodMax;

            if (hasBoth) {
                return turnPeriodAndPeriodMaxToString(dosage);
            }

            return turnPeriodToString(dosage);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && dosage.getTiming().getRepeat().hasPeriodUnit()
                && (dosage.getTiming().getRepeat().hasPeriod() ||
                dosage.getTiming().getRepeat().hasPeriodMax());
    }

    private String turnPeriodAndPeriodMaxToString(Dosage dosage) {
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var periodMax = repeat.getPeriodMax();
        var periodMin = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitMsg = bundle.getString("withoutCount." + periodUnit);
        var unitText = MessageFormat.format(unitMsg, periodMax);

        Map<String, Object> arguments = Map.of(
                "maxPeriod", periodMax,
                "minPeriod", periodMin,
                "unit",unitText
        );

        return periodMaxMsg.format(arguments);
    }

    private String turnPeriodToString(Dosage dosage) {
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var period = repeat.getPeriod();
        var periodUnit = repeat.getPeriodUnit().toCode();

        var unitMsg = bundle.getString("withoutCount." + periodUnit);
        var unitText = MessageFormat.format(unitMsg, period);

        Map<String, Object> arguments = Map.of(
                "period", period,
                "periodUnit",unitText
        );

        return periodMsg.format(arguments);

    }
}
