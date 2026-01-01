package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFrequencyFrequencyMaxPeriodPeriodMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequencyOrPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage= generateEmptyDosage();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyOnly();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithFrequencyOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithPeriodOnly();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithPeriodOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothFrequencyAndPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothFrequencyAndPeriod();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateBothFrequencyAndPeriod();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3 Mal";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "3 veces";
        } else {
            return "3 keer";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "every 2 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "chaque 2 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "alle 2 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "cada 2 días";
        } else {
            return "per 2 dagen";
        }
    }

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3 times every 2 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3 fois chaque 2 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3 Mal alle 2 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "3 veces cada 2 días";
        } else {
            return "3 keer per 2 dagen";
        }
    }
}