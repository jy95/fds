package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.period" / "timing.repeat.periodMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface PeriodPeriodMax<C extends FDSConfig, D> extends TranslatorTiming<C, D> {
    
    /**
     * MessageFormat instance used for "period" &amp; "periodMax" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "period" &amp; "periodMax"
     */
    default MessageFormat getPeriodMaxMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.periodMax");
        return new MessageFormat(msg, locale);
    }

    /**
     * MessageFormat instance used for "period" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "period"
     */
    default MessageFormat getPeriodMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.period");
        return new MessageFormat(msg, locale);
    }

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D dosage) {
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
    boolean hasPeriod(D dosage);

    /**
     * Checks whether a periodMax value is present in the data object.
     *
     * @param dosage The data object to check.
     * @return {@code true} if a periodMax value is present; {@code false} otherwise.
     */
    boolean hasPeriodMax(D dosage);

    /**
     * Converts both period and periodMax values into a formatted string.
     *
     * @param dosage The data object containing the values.
     * @return A formatted string representing both period and periodMax.
     */
    String turnPeriodAndPeriodMaxToString(D dosage);

    /**
     * Converts the period value into a formatted string.
     *
     * @param dosage The data object containing the period value.
     * @return A formatted string representing the period.
     */
    String turnPeriodToString(D dosage);
}
