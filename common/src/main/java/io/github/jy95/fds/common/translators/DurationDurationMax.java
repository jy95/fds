package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;

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
public interface DurationDurationMax<D> extends TranslatorTiming<D> {

    /**
     * Key constants for duration message
     */
    String KEY_DURATION = "fields.duration";
    /**
     * Key constant for durationMax message
     */
    String KEY_DURATION_MAX = "fields.durationMax";

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: duration SHALL be a non-negative value
            // Rule: if there's a duration, there needs to be duration units
            // Rule: If there's a durationMax, there must be a duration
            var hasDurationFlag = hasDuration(dosage);
            var hasDurationMaxFlag = hasDurationMax(dosage);
            var hasBoth = hasDurationFlag && hasDurationMaxFlag;

            List<String> texts = new ArrayList<>();

            if (hasDurationFlag) {
                texts.add(turnDurationToString(dosage));
            }

            if (hasBoth) {
                texts.add("(");
            }

            if (hasDurationMaxFlag) {
                texts.add(turnDurationMaxToString(dosage));
            }

            if (hasBoth) {
                texts.add(")");
            }

            return String.join(" ", texts);
        });
    }

    /**
     * Determines if the dosage data contains a valid "duration" value.
     *
     * @param dosage the dosage data.
     * @return true if the dosage contains a "duration" value, false otherwise.
     */
    boolean hasDuration(D dosage);

    /**
     * Determines if the dosage data contains a valid "durationMax" value.
     *
     * @param dosage the dosage data.
     * @return true if the dosage contains a "durationMax" value, false otherwise.
     */
    boolean hasDurationMax(D dosage);

    /**
     * Converts the "duration" value in the dosage data into a formatted string.
     *
     * @param dosage the dosage data.
     * @return the formatted string representing the "duration".
     */
    String turnDurationToString(D dosage);

    /**
     * Converts the "durationMax" value in the dosage data into a formatted string.
     *
     * @param dosage the dosage data.
     * @return the formatted string representing the "durationMax".
     */
    String turnDurationMaxToString(D dosage);
}
