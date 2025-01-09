package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPeriodPeriodMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    protected final MessageFormat periodMaxMsg;
    protected final MessageFormat periodMsg;
    
    public AbstractPeriodPeriodMax(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.periodMax");
        var msg2 = bundle.getString("fields.period");
        periodMaxMsg = new MessageFormat(msg1, locale);
        periodMsg = new MessageFormat(msg2, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: if there's a period, there needs to be period units
            // Rule: period SHALL be a non-negative value
            // Rule: If there's a periodMax, there must be a period
            var hasPeriodFlag = hasPeriod(dosage);
            var hasPeriodMaxFlag = hasPeriodMax(dosage);
            var hasBoth = hasPeriodFlag && hasPeriodMaxFlag;

            if (hasBoth) {
                return turnPeriodAndPeriodMaxToString(dosage);
            }

            return turnPeriodToString(dosage);
        });
    }

    protected abstract boolean hasPeriod(D dosage);

    protected abstract boolean hasPeriodMax(D dosage);

    protected abstract String turnPeriodAndPeriodMaxToString(D dosage);

    protected abstract String turnPeriodToString(D dosage);

    protected String formatPeriodText(BigDecimal period, String unitText){
        Map<String, Object> arguments = Map.of(
                "period", period,
                "periodUnit",unitText
        );
        return periodMsg.format(arguments);
    }

    protected String formatPeriodAndPeriodMaxText(BigDecimal periodMin, BigDecimal periodMax, String unitText) {
        Map<String, Object> arguments = Map.of(
                "maxPeriod", periodMax,
                "minPeriod", periodMin,
                "unit",unitText
        );
        return periodMaxMsg.format(arguments);
    }

    protected String getUnit(String periodUnit, BigDecimal amount) {
        var unitMsg = getResources().getString("withoutCount." + periodUnit);
        return MessageFormat.format(unitMsg, amount);
    }
}
