package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTimeOfDayTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSingleTimeOfDay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedSingleTimeText(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateSingleTimeOfDay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultipleTimeOfDay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedMultipleTimesText(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateMultipleTimeOfDay();

    // Expected text for single timeOfDay
    private static String getExpectedSingleTimeText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at 15:00";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à 15:00";
        } else if (locale.equals(Locale.GERMAN)) {
            return "um 15:00";
        } else {
            return "om 15:00";
        }
    }

    // Expected text for multiple timeOfDay
    private static String getExpectedMultipleTimesText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at 15:00 and 12:12:12";
        }
        else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a las 15.00 horas y 12:12:12";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à 15:00 et 12:12:12";
        } else if (locale.equals(Locale.GERMAN)) {
            return "um 15:00 und 12:12:12";
        } else {
            return "om 15:00 en 12:12:12";
        }
    }
}
