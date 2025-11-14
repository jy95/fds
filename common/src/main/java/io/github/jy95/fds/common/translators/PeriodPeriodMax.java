package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.period" / "timing.repeat.periodMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface PeriodPeriodMax<D> extends TranslatorTiming<D> {
    
    // Key constant for periodMax message
    String KEY_PERIOD_MAX = "fields.periodMax";
    // Key constant for period message
    String KEY_PERIOD = "fields.period";

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
