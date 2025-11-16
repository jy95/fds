package io.github.jy95.fds.common.translators;

import io.github.jy95.fds.common.types.TranslatorTiming;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.frequency" /
 * "timing.repeat.frequencyMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface FrequencyFrequencyMax<D> extends TranslatorTiming<D> {

    /**
     * Key constant for frequency message
     */
    String KEY_FREQUENCY = "fields.frequency";
    /**
     * Key constant for frequencyMax message
     */
    String KEY_FREQUENCY_MAX = "fields.frequencyMax";
    /**
     * Key constant for frequencyAndFrequencyMax message
     */
    String KEY_FREQUENCY_AND_FREQUENCY_MAX = "fields.frequencyAndFrequencyMax";

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var hasFrequencyFlag = hasFrequency(dosage);
            var hasFrequencyMaxFlag = hasFrequencyMax(dosage);
            var hasBoth = hasFrequencyFlag && hasFrequencyMaxFlag;

            if (hasBoth) {
                return turnFrequencyAndFrequencyMaxToString(dosage);
            }

            if (hasFrequencyMaxFlag) {
                return turnFrequencyMaxToString(dosage);
            }

            return turnFrequencyToString(dosage);
        });
    }

    /**
     * Formats the text for cases where both "frequency" and "frequencyMax" are
     * present.
     *
     * @param frequencyMin The minimum frequency.
     * @param frequencyMax The maximum frequency.
     * @return A formatted string representing both "frequency" and "frequencyMax".
     */
    String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax);

    /**
     * Formats the text for cases where only "frequencyMax" is present.
     *
     * @param frequencyMax The maximum frequency.
     * @return A formatted string representing "frequencyMax".
     */
    String formatFrequencyMaxText(int frequencyMax);

    /**
     * Formats the text for cases where only "frequency" is present.
     *
     * @param frequency The frequency.
     * @return A formatted string representing "frequency".
     */
    String formatFrequencyText(int frequency);

    /**
     * Checks if the dosage data contains a valid "frequency" value.
     *
     * @param dosage The dosage data.
     * @return true if the dosage contains a "frequency" value, false otherwise.
     */
    boolean hasFrequency(D dosage);

    /**
     * Checks if the dosage data contains a valid "frequencyMax" value.
     *
     * @param dosage The dosage data.
     * @return true if the dosage contains a "frequencyMax" value, false otherwise.
     */
    boolean hasFrequencyMax(D dosage);

    /**
     * Converts the dosage data containing both "frequency" and "frequencyMax" into
     * a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing both "frequency" and "frequencyMax".
     */
    String turnFrequencyAndFrequencyMaxToString(D dosage);

    /**
     * Converts the dosage data containing "frequencyMax" into a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing "frequencyMax".
     */
    String turnFrequencyMaxToString(D dosage);

    /**
     * Converts the dosage data containing "frequency" into a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing "frequency".
     */
    String turnFrequencyToString(D dosage);
}
