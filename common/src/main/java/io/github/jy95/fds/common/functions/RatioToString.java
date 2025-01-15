package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for converting ratio objects to human-readable strings.
 *
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <R> The type of ratio object to be converted.
 * @author jy95
 */
public interface RatioToString<C extends FDSConfig, R> {

    /**
     * Converts a ratio object to a human-readable string asynchronously.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param ratio   The ratio object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    default CompletableFuture<String> convert(ResourceBundle bundle, C config, R ratio) {
        var linkword = retrieveRatioLinkWord(bundle, config, ratio);

        var numeratorText = hasNumerator(ratio)
                ? convertNumerator(bundle, config, ratio)
                : CompletableFuture.completedFuture("");

        var denominatorText = hasDenominator(ratio)
                ? convertDenominator(bundle, config, ratio)
                : CompletableFuture.completedFuture("");

        return numeratorText.thenCombineAsync(denominatorText, (num, dem) -> Stream
                .of(num, linkword, dem)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "))
        );
    }

    /**
     * Retrieves the link word based on the ratio's components.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param ratio    The ratio object.
     * @return The link word as a string.
     */
    String retrieveRatioLinkWord(ResourceBundle bundle, C config, R ratio);

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
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param ratio    The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the numerator.
     */
    CompletableFuture<String> convertNumerator(ResourceBundle bundle, C config, R ratio);

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
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param ratio    The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the denominator.
     */
    CompletableFuture<String> convertDenominator(ResourceBundle bundle, C config, R ratio);
}
