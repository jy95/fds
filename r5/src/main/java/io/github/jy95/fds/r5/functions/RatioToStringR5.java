package io.github.jy95.fds.r5.functions;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Ratio;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for converting ratio objects to human-readable strings.
 * Implements the Bill Pugh Singleton pattern for thread-safe lazy initialization.
 *
 * @author jy95
 */
public class RatioToStringR5 implements RatioToString<FDSConfigR5, Ratio> {

    // Private constructor to prevent instantiation
    private RatioToStringR5() {}

    // Static inner class for holding the singleton instance
    private static class Holder {
        private static final RatioToStringR5 INSTANCE = new RatioToStringR5();
    }

    /**
     * Returns the singleton instance of RatioToStringR5.
     *
     * @return the singleton instance
     */
    public static RatioToStringR5 getInstance() {
        return Holder.INSTANCE;
    }

    /** {@inheritDoc} */
    @Override
    public String retrieveRatioLinkWord(ResourceBundle bundle, FDSConfigR5 config, Ratio ratio) {
        var hasNumerator = ratio.hasNumerator();
        var hasDenominator = ratio.hasDenominator();
        var hasNumeratorUnit = hasNumerator && QuantityToStringR5.getInstance().hasUnit(ratio.getNumerator());
        var hasBothElements = hasNumerator && hasDenominator;
        var hasDenominatorUnit = hasDenominator && QuantityToStringR5.getInstance().hasUnit(ratio.getDenominator());
        var hasUnitRatio = hasNumeratorUnit || hasDenominatorUnit;
        var denominatorValue = hasDenominator ? ratio.getDenominator().getValue() : BigDecimal.ONE;

        if (hasUnitRatio && hasBothElements) {
            var linkWordMsg = bundle.getString("amount.ratio.denominatorLinkword");
            return new MessageFormat(linkWordMsg, config.getLocale()).format(new Object[]{denominatorValue});
        }

        return hasBothElements ? ":" : "";
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasNumerator(Ratio ratio) {
        return ratio.hasNumerator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertNumerator(ResourceBundle bundle, FDSConfigR5 config, Ratio ratio) {
        return QuantityToStringR5
                .getInstance()
                .convert(bundle, config, ratio.getNumerator());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasDenominator(Ratio ratio) {
        return ratio.hasDenominator();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertDenominator(ResourceBundle bundle, FDSConfigR5 config, Ratio ratio) {
        var denominator = ratio.getDenominator();
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = denominator.getValue();

        // For titers cases (e.g., 1:128)
        if (!QuantityToStringR5.getInstance().hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return QuantityToStringR5
                    .getInstance()
                    .enhancedFromUnitToString(bundle, config, denominator);
        }

        return QuantityToStringR5
                .getInstance()
                .convert(bundle, config, denominator);
    }
}
