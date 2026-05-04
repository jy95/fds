package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.functions.UnitsOfTimeFormatter;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.Builder;
import lombok.Value;

/**
 * Translator for "timing.repeat.duration" /
 * "timing.repeat.durationMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 2.1.9
 */
@Builder
@Value
public class DurationDurationMax<D> implements Translator<D> {

    /** Translation service */
    private final TranslationService<?> translationService;
    /** Predicate to check if "durationUnit" is present */
    private final Predicate<D> hasDurationUnit;
    /** Predicate to check if "duration" is present */
    private final Predicate<D> hasDuration;
    /** Predicate to check if "durationMax" is present */
    private final Predicate<D> hasDurationMax;
    /** Function to get the "durationUnit" value from the data */
    private final Function<D, String> getDurationUnit;
    /** Function to get the "duration" value from the data */
    private final Function<D, BigDecimal> getDuration;
    /** Function to get the "durationMax" value from the data */
    private final Function<D, BigDecimal> getDurationMax;

    /**
     * Pattern with both duration are present
     */
    @Builder.Default
    private final String DURATION_DURATION_MAX = "%s ( %s )";

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D data) {

        var hasAnyDuration = GenericOperations.anyMatchLazy(
                () -> hasDuration.test(data),
                () -> hasDurationMax.test(data));

        return GenericOperations.allMatchLazy(
                () -> hasDurationUnit.test(data),
                () -> hasAnyDuration);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: duration SHALL be a non-negative value
            // Rule: if there's a duration, there needs to be duration units
            // Rule: If there's a durationMax, there must be a duration
            var hasDurationFlag = hasDuration.test(data);
            var hasDurationMaxFlag = hasDurationMax.test(data);
            var hasBoth = GenericOperations.allMatchLazy(
                    () -> hasDurationFlag,
                    () -> hasDurationMaxFlag);

            if (hasBoth) {
                return String.format(
                        DURATION_DURATION_MAX,
                        turnDurationToString(data),
                        turnDurationMaxToString(data));
            }

            return GenericOperations.conditionalSelect(
                    hasDurationMaxFlag,
                    () -> turnDurationMaxToString(data),
                    () -> turnDurationToString(data));
        });
    }


    /**
     * Converts the "duration" value in the data data into a formatted string.
     *
     * @param data The data to convert.
     * @return the formatted string representing the "duration".
     */
    private String turnDurationToString(D data) {
        var durationUnit = getDurationUnit.apply(data);
        var durationQuantity = getDuration.apply(data);

        var locale = translationService.getConfig().getLocale();
        var durationMsg = translationService.getMessage("fields.duration");

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMsg.format(new Object[]{durationText});
    }

    /**
     * Converts the "durationMax" value in the data data into a formatted string.
     *
     * @param data The data to convert.
     * @return the formatted string representing the "durationMax".
     */
    private String turnDurationMaxToString(D data) {
        var durationUnit = getDurationUnit.apply(data);
        var durationQuantity = getDurationMax.apply(data);

        var locale = translationService.getConfig().getLocale();
        var durationMaxMsg = translationService.getMessage("fields.durationMax");

        var durationText = UnitsOfTimeFormatter.formatWithCount(locale, durationUnit, durationQuantity);
        return durationMaxMsg.format(new Object[]{durationText});
    }
}
