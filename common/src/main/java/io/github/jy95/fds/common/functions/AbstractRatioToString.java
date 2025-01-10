package io.github.jy95.fds.common.functions;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.github.jy95.fds.common.config.FDSConfig;

/**
 * Abstract base class for converting ratio objects to human-readable strings.
 * @param <C> The type of configuration object extending FDSConfig.
 * @param <R> The type of ratio object to be converted.
 */
public abstract class AbstractRatioToString<C extends FDSConfig, R> {
    
    /**
     * Converts a ratio object to a human-readable string asynchronously.
     *
     * @param bundle  The resource bundle for localization.
     * @param config  The configuration object for additional logic.
     * @param ratio   The ratio object to convert.
     * @return A CompletableFuture that resolves to the human-readable string.
     */
    public CompletableFuture<String> convert(ResourceBundle bundle, C config, R ratio) {
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
    protected abstract String retrieveRatioLinkWord(ResourceBundle bundle, C config, R ratio);

    /**
     * Determines if the ratio has a numerator.
     *
     * @param ratio The ratio object.
     * @return True if the ratio has a numerator, false otherwise.
     */
    protected abstract boolean hasNumerator(R ratio);

    /**
     * Converts the numerator to a human-readable string.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param ratio    The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the numerator.
     */
    protected abstract CompletableFuture<String> convertNumerator(ResourceBundle bundle, C config, R ratio);

    /**
     * Determines if the ratio has a denominator.
     *
     * @param ratio The ratio object.
     * @return True if the ratio has a denominator, false otherwise.
     */
    protected abstract boolean hasDenominator(R ratio);

    /**
     * Converts the denominator to a human-readable string.
     *
     * @param bundle   The resource bundle for localization.
     * @param config   The configuration object for additional logic.
     * @param ratio    The ratio object.
     * @return A CompletableFuture that resolves to the human-readable string for the denominator.
     */
    protected abstract CompletableFuture<String> convertDenominator(ResourceBundle bundle, C config, R ratio);
}
