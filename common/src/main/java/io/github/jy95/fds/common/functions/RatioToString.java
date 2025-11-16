package io.github.jy95.fds.common.functions;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.operations.QuantityProcessor;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hl7.fhir.instance.model.api.IBase;

/**
 * Interface for converting ratio objects to human-readable strings.
 *
 * @param <R> The type of ratio object to be converted.
 * @param <Q> The type of quantity object to be converted.
 * @param <C> The type of configuration object extending FDSConfig.
 * @author jy95
 * @since 1.0.0
 */
public interface RatioToString<R, Q extends IBase, C extends FDSConfig & QuantityProcessor<Q>> {

    /**
     * Retrieves the utility class for processing Quantity within the ratio object
     * 
     * @return a QuantityToString bound to the FHIR version
     */
    QuantityToString<Q, C> getQuantityToString();

    /**
     * Retrieve the numerator
     * 
     * @param ratio The ratio object.
     * @return The "numerator" quantity of the ratio
     */
    Q getNumerator(R ratio);

    /**
     * Retrieve the denominator
     * 
     * @param ratio The ratio object.
     * @return The "denominator" quantity of the ratio
     */
    Q getDenominator(R ratio);

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
        var hasNum = hasNumerator(ratio);
        var hasDen = hasDenominator(ratio);
        var hasBoth = hasNum && hasDen;

        if (!hasBoth) {
            return "";
        }

        if (hasUnitRatio(ratio)) {
            var solver = getQuantityToString();
            var denominatorValue = solver.getValue(getDenominator(ratio));

            var linkWordMsg = translationService.getMessage("amount.ratio.denominatorLinkword");
            return linkWordMsg.format(new Object[]{denominatorValue});
        }

        return ":";
    }


    /**
     * Determines if the ratio has a unit in either numerator or denominator.
     *
     * @param ratio The ratio object.
     * @return True if either the numerator or denominator has a unit, false otherwise.
     */
    default boolean hasUnitRatio(R ratio) {
        var solver = getQuantityToString();

        var hasNumeratorUnit = hasNumerator(ratio) && solver.hasUnit(getNumerator(ratio));
        var hasDenominatorUnit = hasDenominator(ratio) && solver.hasUnit(getDenominator(ratio));
        return hasNumeratorUnit || hasDenominatorUnit;
    }

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
    default CompletableFuture<String> convertNumerator(TranslationService<C> translationService, R ratio) {
        var solver = getQuantityToString();
        return solver.convert(translationService, getNumerator(ratio));
    }

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
    default CompletableFuture<String> convertDenominator(TranslationService<C> translationService, R ratio) {
        var solver = getQuantityToString();
        
        var denominator = getDenominator(ratio);
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = solver.getValue(denominator);

        // For titers cases (e.g., 1:128)
        if (!solver.hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return solver.enhancedFromUnitToString(translationService, denominator);
        }

        return solver.convert(translationService, denominator);
    }
}