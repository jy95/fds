package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractRateRatioTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateRatio(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testEmptyRateRatio(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyRateRatio();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    protected abstract D generateEmptyRateRatio();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyNumerator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyNumerator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateOnlyNumerator();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "au taux de 10 ml";
            case "de"    -> "mit einem Verhältnis von 10 ml";
            case "es"    -> "a una tasa de 10 ml";
            case "it"    -> "ad un tasso di 10 ml";
            case "nl-BE" -> "met een verhouding van 10 ml";
            default      -> "at a rate of 10 ml";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyDenominator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyDenominator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateOnlyDenominator();

    private String getExpectedText2(Locale locale) {
        return getExpectedText1(locale);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNumeratorAndDenominator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateNumeratorAndDenominator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateNumeratorAndDenominator();

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "au taux de 10 mg par ml";
            case "de"    -> "mit einem Verhältnis von 10 mg pro ml";
            case "es"    -> "a una tasa de 10 mg por ml";
            case "it"    -> "ad un tasso di 10 mg per ml";
            case "nl-BE" -> "met een verhouding van 10 mg per ml";
            default      -> "at a rate of 10 mg per ml";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testTiterCase(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateTiterCase();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText4(locale), result);
    }

    protected abstract D generateTiterCase();

    private String getExpectedText4(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "au taux de 1 : 128";
            case "de"    -> "mit einem Verhältnis von 1 : 128";
            case "es"    -> "a una tasa de 1 : 128";
            case "it"    -> "ad un tasso di 1 : 128";
            case "nl-BE" -> "met een verhouding van 1 : 128";
            default      -> "at a rate of 1 : 128";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testCommon(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateCommonCase();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText5(locale), result);
    }

    protected abstract D generateCommonCase();

    private String getExpectedText5(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "au taux de 10 mg chaque 2 ml";
            case "de"    -> "mit einem Verhältnis von 10 mg jeder 2 ml";
            case "es"    -> "a una tasa de 10 mg cada 2 ml";
            case "it"    -> "ad un tasso di 10 mg ogni 2 ml";
            case "nl-BE" -> "met een verhouding van 10 mg elke 2 ml";
            default      -> "at a rate of 10 mg every 2 ml";
        };
    }
}