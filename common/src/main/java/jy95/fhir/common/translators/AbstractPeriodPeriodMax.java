package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslatorTiming;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "timing.repeat.period" / "timing.repeat.periodMax".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractPeriodPeriodMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "period" &amp; "periodMax" translation */
    protected final MessageFormat periodMaxMsg;
    /** MessageFormat instance used for "period" */
    protected final MessageFormat periodMsg;

    /**
     * Constructor for {@code AbstractPeriodPeriodMax}.
     * @param config The configuration object used for translation.
     */
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

    /**
     * Checks whether a period value is present in the data object.
     *
     * @param dosage The data object to check.
     * @return {@code true} if a period value is present; {@code false} otherwise.
     */
    protected abstract boolean hasPeriod(D dosage);

    /**
     * Checks whether a periodMax value is present in the data object.
     *
     * @param dosage The data object to check.
     * @return {@code true} if a periodMax value is present; {@code false} otherwise.
     */
    protected abstract boolean hasPeriodMax(D dosage);

    /**
     * Converts both period and periodMax values into a formatted string.
     *
     * @param dosage The data object containing the values.
     * @return A formatted string representing both period and periodMax.
     */
    protected abstract String turnPeriodAndPeriodMaxToString(D dosage);

    /**
     * Converts the period value into a formatted string.
     *
     * @param dosage The data object containing the period value.
     * @return A formatted string representing the period.
     */
    protected abstract String turnPeriodToString(D dosage);

    /**
     * Formats a single period value with its unit.
     *
     * @param period The period value.
     * @param unitText The localized unit of the period.
     * @return A formatted string representing the period and its unit.
     */
    protected String formatPeriodText(BigDecimal period, String unitText){
        Map<String, Object> arguments = Map.of(
                "period", period,
                "periodUnit",unitText
        );
        return periodMsg.format(arguments);
    }

    /**
     * Formats both period and periodMax values with their shared unit.
     *
     * @param periodMin The minimum period value.
     * @param periodMax The maximum period value.
     * @param unitText The localized unit of the periods.
     * @return A formatted string representing the range of periods and their unit.
     */
    protected String formatPeriodAndPeriodMaxText(BigDecimal periodMin, BigDecimal periodMax, String unitText) {
        Map<String, Object> arguments = Map.of(
                "maxPeriod", periodMax,
                "minPeriod", periodMin,
                "unit",unitText
        );
        return periodMaxMsg.format(arguments);
    }

    /**
     * Retrieves the localized unit name for the given period unit and amount.
     *
     * @param periodUnit The unit code of the period (e.g., "d", "h").
     * @param amount The quantity associated with the period unit.
     * @return A localized string representing the unit.
     */
    protected String getUnit(String periodUnit, BigDecimal amount) {
        var unitMsg = getResources().getString("withoutCount." + periodUnit);
        return MessageFormat.format(unitMsg, amount);
    }
}
