package io.github.jy95.fds.common.translators.timing.repeat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.GenericOperations;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Translator for "timing.repeat.frequency" /
 * "timing.repeat.frequencyMax".
 *
 * @param <D> The type of the translated data.
 * @param <C> The configuration type extending FDSConfig.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class FrequencyFrequencyMax<D, C extends FDSConfig> implements Translator<D> {

    /* The translation service used for fetching localized messages. */
    private final TranslationService<C> translationService;
    /* Predicate to check if the data contains a valid "frequency" value. */
    private final Predicate<D> hasFrequency;
    /* Predicate to check if the data contains a valid "frequencyMax" value. */
    private final Predicate<D> hasFrequencyMax;
    /* Function to extract the "frequency" value from the data. */
    private final Function<D, Integer> getFrequency;
    /* Function to extract the "frequencyMax" value from the data. */
    private final Function<D, Integer> getFrequencyMax;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D data) {
        return GenericOperations.anyMatchLazy(
            () -> hasFrequency.test(data),
            () -> hasFrequencyMax.test(data)
        );
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            var hasFrequencyFlag = hasFrequency.test(data);
            var hasFrequencyMaxFlag = hasFrequencyMax.test(data);
            var hasBoth = GenericOperations.allMatchLazy(
                () -> hasFrequencyFlag,
                () -> hasFrequencyMaxFlag
            );

            if (hasBoth) {
                return turnFrequencyAndFrequencyMaxToString(data);
            }

            return GenericOperations.conditionalSelect(
                hasFrequencyMaxFlag, 
                () -> turnFrequencyMaxToString(data), 
                () -> turnFrequencyToString(data)
            );
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
    private String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax) {
        Map<String, Object> arguments = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax);
        var frequencyAndFrequencyMaxMsg = translationService.getMessage("fields.frequencyAndFrequencyMax");
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    /**
     * Formats the text for cases where only "frequencyMax" is present.
     *
     * @param frequencyMax The maximum frequency.
     * @return A formatted string representing "frequencyMax".
     */
    private String formatFrequencyMaxText(int frequencyMax) {
        var frequencyMaxMsg = translationService.getMessage("fields.frequencyMax");
        return frequencyMaxMsg.format(new Object[] { frequencyMax });
    }

    /**
     * Formats the text for cases where only "frequency" is present.
     *
     * @param frequency The frequency.
     * @return A formatted string representing "frequency".
     */
    private String formatFrequencyText(int frequency) {
        var frequencyMsg = translationService.getMessage("fields.frequency");
        return frequencyMsg.format(new Object[] { frequency });
    }

    /**
     * Converts the data containing both "frequency" and "frequencyMax" into
     * a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing both "frequency" and "frequencyMax".
     */
    private String turnFrequencyAndFrequencyMaxToString(D data) {
        int frequency = getFrequency.apply(data);
        int frequencyMax = getFrequencyMax.apply(data);
        return formatFrequencyAndFrequencyMaxText(frequency, frequencyMax);
    }

    /**
     * Converts the data containing "frequencyMax" into a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing "frequencyMax".
     */
    private String turnFrequencyMaxToString(D data) {
        int frequencyMax = getFrequencyMax.apply(data);
        return formatFrequencyMaxText(frequencyMax);
    }

    /**
     * Converts the data containing "frequency" into a formatted string.
     *
     * @param data The data to convert.
     * @return A formatted string representing "frequency".
     */
    private String turnFrequencyToString(D data) {
        int frequency = getFrequency.apply(data);
        return formatFrequencyText(frequency);
    }
}
