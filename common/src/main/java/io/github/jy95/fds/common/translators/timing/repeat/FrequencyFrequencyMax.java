package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating "timing.repeat.frequency" /
 * "timing.repeat.frequencyMax".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface FrequencyFrequencyMax<D, C extends FDSConfig> extends Translator<D> {

    /**
     * Return the TranslationService responsible for handling frequency
     * 
     * @return the TranslationService
     */
    TranslationService<C> getTranslationService();

    /** {@inheritDoc} */
    @Override
    default CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            var hasFrequencyFlag = hasFrequency(data);
            var hasFrequencyMaxFlag = hasFrequencyMax(data);
            var hasBoth = hasFrequencyFlag && hasFrequencyMaxFlag;

            if (hasBoth) {
                return turnFrequencyAndFrequencyMaxToString(data);
            }

            if (hasFrequencyMaxFlag) {
                return turnFrequencyMaxToString(data);
            }

            return turnFrequencyToString(data);
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
    default String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax) {
        Map<String, Object> arguments = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );
        var frequencyAndFrequencyMaxMsg = getTranslationService().getMessage("fields.frequencyAndFrequencyMax");
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    /**
     * Formats the text for cases where only "frequencyMax" is present.
     *
     * @param frequencyMax The maximum frequency.
     * @return A formatted string representing "frequencyMax".
     */
    default String formatFrequencyMaxText(int frequencyMax) {
        var frequencyMaxMsg = getTranslationService().getMessage("fields.frequencyMax");
        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    /**
     * Formats the text for cases where only "frequency" is present.
     *
     * @param frequency The frequency.
     * @return A formatted string representing "frequency".
     */
    default String formatFrequencyText(int frequency) {
        var frequencyMsg = getTranslationService().getMessage("fields.frequency");
        return frequencyMsg.format(new Object[]{frequency});
    }

    /**
     * Checks if the data contains a valid "frequency" value.
     *
     * @param data The data to check.
     * @return true if the data contains a "frequency" value, false otherwise.
     */
    boolean hasFrequency(D data);

    /**
     * Checks if the data contains a valid "frequencyMax" value.
     *
     * @param data The data to check.
     * @return true if the data contains a "frequencyMax" value, false otherwise.
     */
    boolean hasFrequencyMax(D data);

    /**
     * Converts the data containing both "frequency" and "frequencyMax" into
     * a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing both "frequency" and "frequencyMax".
     */
    String turnFrequencyAndFrequencyMaxToString(D data);

    /**
     * Converts the data containing "frequencyMax" into a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing "frequencyMax".
     */
    String turnFrequencyMaxToString(D data);

    /**
     * Converts the data containing "frequency" into a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing "frequency".
     */
    String turnFrequencyToString(D data);
}
