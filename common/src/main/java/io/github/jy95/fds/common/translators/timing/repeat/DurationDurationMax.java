package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.types.Translator;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.List;

/**
 * Interface for translating "timing.repeat.duration" /
 * "timing.repeat.durationMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface DurationDurationMax<D> extends Translator<D> {

    /**
     * Key constant for duration message
     */
    String KEY_DURATION = "fields.duration";
    /**
     * Key constant for durationMax message
     */
    String KEY_DURATION_MAX = "fields.durationMax";

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: duration SHALL be a non-negative value
            // Rule: if there's a duration, there needs to be duration units
            // Rule: If there's a durationMax, there must be a duration
            var hasDurationFlag = hasDuration(data);
            var hasDurationMaxFlag = hasDurationMax(data);
            var hasBoth = hasDurationFlag && hasDurationMaxFlag;

            List<String> texts = new ArrayList<>();

            if (hasDurationFlag) {
                texts.add(turnDurationToString(data));
            }

            if (hasBoth) {
                texts.add("(");
            }

            if (hasDurationMaxFlag) {
                texts.add(turnDurationMaxToString(data));
            }

            if (hasBoth) {
                texts.add(")");
            }

            return String.join(" ", texts);
        });
    }

    /**
     * Determines if the data data contains a valid "duration" value.
     *
     * @param data The data to convert.
     * @return true if the data contains a "duration" value, false otherwise.
     */
    boolean hasDuration(D data);

    /**
     * Determines if the data data contains a valid "durationMax" value.
     *
     * @param data The data to convert.
     * @return true if the data contains a "durationMax" value, false otherwise.
     */
    boolean hasDurationMax(D data);

    /**
     * Converts the "duration" value in the data data into a formatted string.
     *
     * @param data The data to convert.
     * @return the formatted string representing the "duration".
     */
    String turnDurationToString(D data);

    /**
     * Converts the "durationMax" value in the data data into a formatted string.
     *
     * @param data The data to convert.
     * @return the formatted string representing the "durationMax".
     */
    String turnDurationMaxToString(D data);
}
