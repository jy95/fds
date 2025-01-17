package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface FrequencyFrequencyMax<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "frequency" &amp; "frequencyMax" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "frequency" &amp; "frequencyMax" translation
     */
    default MessageFormat getFrequencyAndFrequencyMaxMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.frequencyAndFrequencyMax");
        return new MessageFormat(msg, locale);
    }

    /**
     * MessageFormat instance used for "frequencyMax" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "frequencyMax" translation
     */
    default MessageFormat getFrequencyMaxMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.frequencyMax");
        return new MessageFormat(msg, locale);
    }

    /**
     * MessageFormat instance used for "frequency" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "frequency" translation
     */
    default MessageFormat getFrequencyMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.frequency");
        return new MessageFormat(msg, locale);
    }

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
     * Formats the text for cases where both "frequency" and "frequencyMax" are present.
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
     * Converts the dosage data containing both "frequency" and "frequencyMax" into a formatted string.
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
