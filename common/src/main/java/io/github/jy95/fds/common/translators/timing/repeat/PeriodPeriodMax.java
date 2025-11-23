package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.period" / "timing.repeat.periodMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface PeriodPeriodMax<D> extends Translator<D> {

    /**
     * Key constant for periodMax message
     */
    String KEY_PERIOD_MAX = "fields.periodMax";
    /**
     * Key constant for period message
     */
    String KEY_PERIOD = "fields.period";

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: if there's a period, there needs to be period units
            // Rule: period SHALL be a non-negative value
            // Rule: If there's a periodMax, there must be a period
            var hasPeriodFlag = hasPeriod(data);
            var hasPeriodMaxFlag = hasPeriodMax(data);
            var hasBoth = hasPeriodFlag && hasPeriodMaxFlag;

            if (hasBoth) {
                return turnPeriodAndPeriodMaxToString(data);
            }

            return turnPeriodToString(data);
        });
    }

    /**
     * Checks whether a period value is present in the data object.
     *
     * @param data The data object to check.
     * @return {@code true} if a period value is present; {@code false} otherwise.
     */
    boolean hasPeriod(D data);

    /**
     * Checks whether a periodMax value is present in the data object.
     *
     * @param data The data object to check.
     * @return {@code true} if a periodMax value is present; {@code false}
     *         otherwise.
     */
    boolean hasPeriodMax(D data);

    /**
     * Converts both period and periodMax values into a formatted string.
     *
     * @param data The data object containing the values.
     * @return A formatted string representing both period and periodMax.
     */
    String turnPeriodAndPeriodMaxToString(D data);

    /**
     * Converts the period value into a formatted string.
     *
     * @param data The data object containing the period value.
     * @return A formatted string representing the period.
     */
    String turnPeriodToString(D data);

    /** {@inheritDoc} */
    @Override
    default boolean isPresent(D data) {
        return hasPeriod(data) || hasPeriodMax(data);
    }
}
