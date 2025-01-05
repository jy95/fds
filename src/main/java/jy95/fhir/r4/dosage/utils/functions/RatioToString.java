package jy95.fhir.r4.dosage.utils.functions;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Ratio;

import java.math.BigDecimal;
import com.ibm.icu.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jy95.fhir.r4.dosage.utils.functions.QuantityToString.hasUnit;

public final class RatioToString {
    public static CompletableFuture<String> convert(ResourceBundle bundle, FDUConfig config, Ratio ratio) {
        var linkword = retrieveRatioLinkWord(bundle, config, ratio);

        var numeratorText = ratio.hasNumerator()
                ? QuantityToString.convert(bundle, config, ratio.getNumerator())
                : CompletableFuture.completedFuture("");

        var denominatorText = ratio.hasDenominator()
                ? turnDenominatorToText(bundle, config, ratio)
                : CompletableFuture.completedFuture("");

        return numeratorText.thenCombineAsync(denominatorText, (num, dem) -> Stream
                .of(num, linkword, dem)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "))
        );
    }

    private static String retrieveRatioLinkWord(ResourceBundle bundle, FDUConfig config, Ratio ratio) {
        var hasNumerator = ratio.hasNumerator();
        var hasDenominator = ratio.hasDenominator();
        var hasNumeratorUnit = hasNumerator && hasUnit(ratio.getNumerator());
        var hasBothElements = hasNumerator && hasDenominator;
        var hasDenominatorUnit = hasDenominator && hasUnit(ratio.getDenominator());
        var hasUnitRatio = hasNumeratorUnit || hasDenominatorUnit;
        var denominatorValue = hasDenominator ? ratio.getDenominator().getValue() : BigDecimal.ONE;

        if (hasUnitRatio && hasBothElements) {
            var linkWordMsg = bundle.getString("amount.ratio.denominatorLinkword");
            return new MessageFormat(linkWordMsg, config.getLocale()).format(new Object[]{denominatorValue});
        }

        return hasBothElements ? ":" : "";
    }

    private static CompletableFuture<String> turnDenominatorToText(
            ResourceBundle bundle,
            FDUConfig config,
            Ratio ratio
    ) {
        var denominator = ratio.getDenominator();
        // Where the denominator value is known to be fixed to "1", Quantity should be used instead of Ratio
        var denominatorValue = denominator.getValue();

        // For titers cases (e.g. 1:128)
        if (!hasUnit(denominator)) {
            return CompletableFuture.completedFuture(denominatorValue.toString());
        }

        // For the per case
        if (BigDecimal.ONE.equals(denominatorValue)) {
            return QuantityToString.enhancedFromFHIRQuantityUnitToString(bundle, config, denominator);
        }

        return QuantityToString.convert(bundle, config, denominator);
    }
}