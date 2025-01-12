package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "timing.repeat.boundsPeriod".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractBoundsPeriod<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    /** MessageFormat instance used for "boundsPeriod" translation. */
    protected final MessageFormat boundsPeriodMsg;

    /**
     * Constructor for {@code AbstractBoundsPeriod}.
     * @param config The configuration object used for translation.
     */
    public AbstractBoundsPeriod(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsPeriod");
        boundsPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            // Check conditions
            var hasStart = hasStartPeriod(dosage);
            var hasEnd = hasEndPeriod(dosage);

            // Prepare date values using FormatDateTimes.convert()
            String startDate = hasStart ? formatStartPeriod(dosage) : "";
            String endDate = hasEnd ? formatEndPeriod(dosage) : "";

                    // Choose the correct condition based on the presence of start and end dates
            String condition = hasStart && hasEnd ? "0" : (hasStart ? "1" : "other");

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "startDate", startDate,
                    "endDate", endDate,
                    "condition", condition
            );

            // Format the message with the named arguments
            return boundsPeriodMsg.format(arguments);
        });
    }

    /**
     * Check if dosage has a "start" period
     * @param dosage the dosage object to check
     */
    protected abstract boolean hasStartPeriod(D dosage);

    /**
     * Check if dosage has a "end" period
     * @param dosage the dosage object to check
     */
    protected abstract boolean hasEndPeriod(D dosage);

    /**
     * Format start period to a human-readable string
     * @param dosage the dosage field to be converted
     */
    protected abstract String formatStartPeriod(D dosage);

    /**
     * Format end period to a human-readable string
     * @param dosage the dosage field to be converted
     */
    protected abstract String formatEndPeriod(D dosage);
}
