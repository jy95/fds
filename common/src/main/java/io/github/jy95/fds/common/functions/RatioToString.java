package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for converting ratio objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <R> The type of ratio object to be converted.
 * @author jy95
 * @since 1.0.0
 */
public interface RatioToString<C extends FDSConfig, R> {

    /**
     * Converts a ratio object to a human-readable string asynchronously.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param ratio              The ratio object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(TranslationService<C> translationService, R ratio) {
        var separator = retrieveRatioLinkWord(translationService, ratio); 

        var numeratorText = hasNumerator(ratio)
                ? convertNumerator(translationService, ratio)
                : CompletableFuture.completedFuture("");

        var denominatorText = hasDenominator(ratio)
                ? convertDenominator(translationService, ratio)
                : CompletableFuture.completedFuture("");

        return numeratorText.thenCombineAsync(denominatorText, (num, dem) -> {            
            return Stream
                .of(num, separator, dem)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "));
        });
    }

    /**
     * Retrieves the link word based on the ratio's components.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param ratio              The ratio object.
     * @return The link word as a string.
     */
    default String retrieveRatioLinkWord(TranslationService<C> translationService, R ratio) {
        var hasNumerator = hasNumerator(ratio);
        var hasDenominator = hasDenominator(ratio);
        var hasBothElements = hasNumerator && hasDenominator;

        var hasUnitRatio = hasUnitRatio(ratio);
        var denominatorValue = getDenominatorValue(ratio);

        if (hasUnitRatio && hasBothElements) {
            var linkWordMsg = translationService.getMessage("amount.ratio.denominatorLinkword");
            return linkWordMsg.format(new Object[]{denominatorValue});
        }

        return hasBothElements ? ":" : "";
    }

    /**
     * Retrieves the denominator value of the ratio.
     *
     * @param ratio The ratio object.
     * @return The denominator value as BigDecimal.
     */
    BigDecimal getDenominatorValue(R ratio);

    /**
     * Determines if the ratio has a unit in either numerator or denominator.
     *
     * @param ratio The ratio object.
     * @return True if either the numerator or denominator has a unit, false otherwise.
     */
    boolean hasUnitRatio(R ratio);

    /**
     * Determines if the ratio has a numerator.
     *
     * @param ratio The ratio object.
     * @return True if the ratio has a numerator, false otherwise.
     */
    boolean hasNumerator(R ratio);

    /**
     * Converts the numerator to a human-readable string.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param ratio              The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the numerator.
     */
    CompletableFuture<String> convertNumerator(TranslationService<C> translationService, R ratio);

    /**
     * Determines if the ratio has a denominator.
     *
     * @param ratio The ratio object.
     * @return True if the ratio has a denominator, false otherwise.
     */
    boolean hasDenominator(R ratio);

    /**
     * Converts the denominator to a human-readable string.
     *
     * @param translationService The service providing localized strings and configuration context.
     * @param ratio              The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the denominator.
     */
    CompletableFuture<String> convertDenominator(TranslationService<C> translationService, R ratio);
}