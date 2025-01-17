package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.List;

/**
 * Interface for translating "timing.repeat.duration" / "timing.repeat.durationMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface DurationDurationMax<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "duration" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "duration"
     */
    default MessageFormat getDurationMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.duration");
        return new MessageFormat(msg, locale);
    }

    /**
     * MessageFormat instance used for "duration" &amp; "durationMax" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "duration" &amp; "durationMax"
     */
    default MessageFormat getDurationMaxMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.durationMax");
        return new MessageFormat(msg, locale);
    }

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
     * Converts the duration quantity and unit into a formatted string.
     *
     * @param bundle The resourceBundle to use
     * @param durationUnit the unit code of duration (e.g., "d", "h").
     * @param quantity the quantity of the duration.
     * @return the formatted string representing the duration.
     */
    default String quantityToString(ResourceBundle bundle, String durationUnit, BigDecimal quantity){
        var commonDurationMsg = bundle.getString("withCount." + durationUnit);
        return MessageFormat.format(commonDurationMsg, quantity);
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
