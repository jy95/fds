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
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testEmptyRateRatio(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyRateRatio();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    protected abstract D generateEmptyRateRatio();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyNumerator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyNumerator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyNumerator();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 ml";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 10 ml";
        } else {
            return "met een verhouding van 10 ml";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyDenominator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyDenominator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyDenominator();

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 ml";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 10 ml";
        } else {
            return "met een verhouding van 10 ml";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNumeratorAndDenominator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateNumeratorAndDenominator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateNumeratorAndDenominator();

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 mg per ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 mg par ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 mg pro ml";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 10 mg por ml";
        } else {
            return "met een verhouding van 10 mg per ml";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testTiterCase(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateTiterCase();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText4(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateTiterCase();

    private String getExpectedText4(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 1 : 128";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 1 : 128";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 1 : 128";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 1 : 128";
        } else {
            return "met een verhouding van 1 : 128";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testCommon(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateCommonCase();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText5(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateCommonCase();

    private String getExpectedText5(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 mg every 2 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 mg chaque 2 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 mg jeder 2 ml";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 10 mg cada 2 ml";
        } else {
            return "met een verhouding van 10 mg elke 2 ml";
        }
    }
}