package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractDayOfWeekTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DAY_OF_WEEK);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSingleDayOfWeek();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DAY_OF_WEEK);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedSingleDayText(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateSingleDayOfWeek();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultipleDayOfWeek();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DAY_OF_WEEK);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedMultipleDaysText(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateMultipleDayOfWeek();

    // For the parametrized test of single form
    private static String getExpectedSingleDayText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Friday";
        } else if (locale.equals(Locale.FRENCH)) {
            return "le vendredi";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am Freitag";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "el viernes";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "venerdì";
        } else {
            return "op vrijdag";
        }
    }

    // For the parametrized test of multiple form
    private String getExpectedMultipleDaysText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Monday and Friday";
        } else if (locale.equals(Locale.FRENCH)) {
            return "les lundi et vendredi";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am Montag und Freitag";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "los lunes y viernes";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "lunedì e venerdì";
        } else {
            return "op maandag en vrijdag";
        }
    }
}