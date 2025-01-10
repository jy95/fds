package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.List;

/**
 * An abstract class for translating "timing.repeat.duration" / "timing.repeat.durationMax".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractDurationDurationMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    /** MessageFormat instance used for "duration" translation. */
    protected final MessageFormat durationMsg;
    /** MessageFormat instance used for "duration" &amp; "durationMax" translation */
    protected final MessageFormat durationMaxMsg;

    /**
     * Constructor for {@code AbstractDurationDurationMax}.
     * @param config The configuration object used for translation.
     */
    public AbstractDurationDurationMax(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.duration");
        var msg2 = bundle.getString("fields.durationMax");
        durationMsg = new MessageFormat(msg1, locale);
        durationMaxMsg = new MessageFormat(msg2, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
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
     * @param durationUnit the unit code of duration (e.g., "d", "h").
     * @param quantity the quantity of the duration.
     * @return the formatted string representing the duration.
     */
    protected String quantityToString(String durationUnit, BigDecimal quantity){
        var bundle = this.getResources();
        var commonDurationMsg = bundle.getString("withCount." + durationUnit);
        return MessageFormat.format(commonDurationMsg, quantity);
    }

    /**
     * Determines if the dosage data contains a valid "duration" value.
     *
     * @param dosage the dosage data.
     * @return true if the dosage contains a "duration" value, false otherwise.
     */
    protected abstract boolean hasDuration(D dosage);

    /**
     * Determines if the dosage data contains a valid "durationMax" value.
     *
     * @param dosage the dosage data.
     * @return true if the dosage contains a "durationMax" value, false otherwise.
     */
    protected abstract boolean hasDurationMax(D dosage);

    /**
     * Converts the "duration" value in the dosage data into a formatted string.
     *
     * @param dosage the dosage data.
     * @return the formatted string representing the "duration".
     */
    protected abstract String turnDurationToString(D dosage);

    /**
     * Converts the "durationMax" value in the dosage data into a formatted string.
     *
     * @param dosage the dosage data.
     * @return the formatted string representing the "durationMax".
     */
    protected abstract String turnDurationMaxToString(D dosage);
}
