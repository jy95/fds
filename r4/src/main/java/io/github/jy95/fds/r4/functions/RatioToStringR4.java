package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Ratio;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * R4 enum for converting ratio objects to human-readable strings.
 *
 * @author jy95
 */
public enum RatioToStringR4 implements RatioToString<FDSConfigR4, Ratio> {

    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasNumerator(Ratio ratio) {
        return ratio.hasNumerator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertNumerator(TranslationService<FDSConfigR4> translationService, Ratio ratio) {
        return QuantityToStringR4
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
    public CompletableFuture<String> convertDenominator(TranslationService<FDSConfigR4> translationService, Ratio ratio) {
        var denominator = ratio.getDenominator();
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = denominator.getValue();

        // For titers cases (e.g., 1:128)
        if (!QuantityToStringR4.INSTANCE.hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return QuantityToStringR4
                    .INSTANCE
                    .enhancedFromUnitToString(translationService, denominator);
        }

        return QuantityToStringR4
                .INSTANCE
                .convert(translationService, denominator);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasUnitRatio(Ratio ratio) {
        var hasNumeratorUnit = hasNumerator(ratio) && QuantityToStringR4.INSTANCE.hasUnit(ratio.getNumerator());
        var hasDenominatorUnit = hasDenominator(ratio) && QuantityToStringR4.INSTANCE.hasUnit(ratio.getDenominator());
        return hasNumeratorUnit || hasDenominatorUnit;
    }

    /** {@inheritDoc} */
    @Override
    public BigDecimal getDenominatorValue(Ratio ratio) {
        return hasDenominator(ratio) ? ratio.getDenominator().getValue() : BigDecimal.ONE;
    }
}
