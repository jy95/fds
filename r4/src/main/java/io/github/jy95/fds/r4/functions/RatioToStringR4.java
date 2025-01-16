package io.github.jy95.fds.r4.functions;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.RatioToString;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Ratio;

import java.math.BigDecimal;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for converting ratio objects to human-readable strings
 *
 * @author jy95
 */
public class RatioToStringR4 implements RatioToString<FDSConfigR4, Ratio> {

    /**
     * Constructor for {@code RatioToStringR4}.
     */
    public RatioToStringR4() {}

    /** {@inheritDoc} */
    @Override
    public String retrieveRatioLinkWord(ResourceBundle bundle, FDSConfigR4 config, Ratio ratio) {
        var hasNumerator = ratio.hasNumerator();
        var hasDenominator = ratio.hasDenominator();
        var hasNumeratorUnit = hasNumerator && QuantityToStringR4.getInstance().hasUnit(ratio.getNumerator());
        var hasBothElements = hasNumerator && hasDenominator;
        var hasDenominatorUnit = hasDenominator && QuantityToStringR4.getInstance().hasUnit(ratio.getDenominator());
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
    public CompletableFuture<String> convertNumerator(ResourceBundle bundle, FDSConfigR4 config, Ratio ratio) {
        return QuantityToStringR4
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
    public CompletableFuture<String> convertDenominator(ResourceBundle bundle, FDSConfigR4 config, Ratio ratio) {
        var denominator = ratio.getDenominator();
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = denominator.getValue();

        // For titers cases (e.g. 1:128)
        if (!QuantityToStringR4.getInstance().hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return QuantityToStringR4
                    .getInstance()
                    .enhancedFromUnitToString(bundle, config, denominator);
        }

        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, denominator);
    }
}
