package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFrequencyFrequencyMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {


    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequency(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithFrequencyOnly();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3 Mal";
        } else {
            return "3 keer";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyMaxOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyMaxOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithFrequencyMaxOnly();

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to 3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu'à 3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu 3 Mal";
        } else {
            return "tot 3 keer";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothFrequency(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothFrequency();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithBothFrequency();

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3-5 times";
        }
        else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "3-5 veces";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3-5 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3-5 Mal";
        } else {
            return "3-5 keer";
        }
    }
}
