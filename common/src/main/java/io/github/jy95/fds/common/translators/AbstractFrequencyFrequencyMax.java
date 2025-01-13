package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "timing.repeat.frequency" / "timing.repeat.frequencyMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractFrequencyFrequencyMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    /** MessageFormat instance used for "frequency" &amp; "frequencyMax" translation */
    protected final MessageFormat frequencyAndFrequencyMaxMsg;
    /** MessageFormat instance used for "frequencyMax" translation */
    protected final MessageFormat frequencyMaxMsg;
    /** MessageFormat instance used for "frequencyMax" translation */
    protected final MessageFormat frequencyMsg;

    /**
     * Constructor for {@code AbstractFrequencyFrequencyMax}.
     *
     * @param config The configuration object used for translation.
     */
    public AbstractFrequencyFrequencyMax(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.frequencyAndFrequencyMax");
        var msg2 = bundle.getString("fields.frequencyMax");
        var msg3 = bundle.getString("fields.frequency");
        frequencyAndFrequencyMaxMsg = new MessageFormat(msg1, locale);
        frequencyMaxMsg = new MessageFormat(msg2, locale);
        frequencyMsg = new MessageFormat(msg3, locale);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D dosage) {
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
    protected String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax) {
        Map<String, Object> arguments = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    /**
     * Formats the text for cases where only "frequencyMax" is present.
     *
     * @param frequencyMax The maximum frequency.
     * @return A formatted string representing "frequencyMax".
     */
    protected String formatFrequencyMaxText(int frequencyMax) {
        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    /**
     * Formats the text for cases where only "frequency" is present.
     *
     * @param frequency The frequency.
     * @return A formatted string representing "frequency".
     */
    protected String formatFrequencyText(int frequency) {
        return frequencyMsg.format(new Object[]{frequency});
    }

    /**
     * Checks if the dosage data contains a valid "frequency" value.
     *
     * @param dosage The dosage data.
     * @return true if the dosage contains a "frequency" value, false otherwise.
     */
    protected abstract boolean hasFrequency(D dosage);

    /**
     * Checks if the dosage data contains a valid "frequencyMax" value.
     *
     * @param dosage The dosage data.
     * @return true if the dosage contains a "frequencyMax" value, false otherwise.
     */
    protected abstract boolean hasFrequencyMax(D dosage);

    /**
     * Converts the dosage data containing both "frequency" and "frequencyMax" into a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing both "frequency" and "frequencyMax".
     */
    protected abstract String turnFrequencyAndFrequencyMaxToString(D dosage);

    /**
     * Converts the dosage data containing "frequencyMax" into a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing "frequencyMax".
     */
    protected abstract String turnFrequencyMaxToString(D dosage);

    /**
     * Converts the dosage data containing "frequency" into a formatted string.
     *
     * @param dosage The dosage data.
     * @return A formatted string representing "frequency".
     */
    protected abstract String turnFrequencyToString(D dosage);
}
