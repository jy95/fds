package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Ratio;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * R5 enum for converting ratio objects to human-readable strings.
 *
 * @author jy95
 */
public enum RatioToStringR5 implements RatioToString<FDSConfigR5, Ratio> {

    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasNumerator(Ratio ratio) {
        return ratio.hasNumerator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertNumerator(TranslationService<FDSConfigR5> translationService, Ratio ratio) {
        return QuantityToStringR5
                .INSTANCE
                .convert(translationService, ratio.getNumerator());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDenominator(Ratio ratio) {
        return ratio.hasDenominator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertDenominator(TranslationService<FDSConfigR5> translationService, Ratio ratio) {
        var denominator = ratio.getDenominator();
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = denominator.getValue();

        // For titers cases (e.g., 1:128)
        if (!QuantityToStringR5.INSTANCE.hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return QuantityToStringR5
                    .INSTANCE
                    .enhancedFromUnitToString(translationService, denominator);
        }

        return QuantityToStringR5
                .INSTANCE
                .convert(translationService, denominator);
    }

    /** {@inheritDoc} */
    @Override
    public BigDecimal getDenominatorValue(Ratio ratio) {
        return hasDenominator(ratio) ? ratio.getDenominator().getValue() : BigDecimal.ONE;
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnitRatio(Ratio ratio) {
        var hasNumeratorUnit = hasNumerator(ratio) && QuantityToStringR5.INSTANCE.hasUnit(ratio.getNumerator());
        var hasDenominatorUnit = hasDenominator(ratio) && QuantityToStringR5.INSTANCE.hasUnit(ratio.getDenominator());
        return hasNumeratorUnit || hasDenominatorUnit;
    }
}