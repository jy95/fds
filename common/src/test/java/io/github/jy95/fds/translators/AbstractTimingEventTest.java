package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTimingEventTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoEvent(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }


    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingle(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSingle();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedSingleDayText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateSingle();

    // For the parametrized test of single form
    private static String getExpectedSingleDayText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Jan 1, 2024";
        } else if (locale.equals(Locale.FRENCH)) {
            return "le 1 janv. 2024";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am 01.01.2024";
        } else {
            return "op 1 jan 2024";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultiple(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultiple();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedMultipleDaysText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateMultiple();

    // For the parametrized test of multiple form
    private String getExpectedMultipleDaysText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Jan 1, 2018, Jun 1, 1973 and Aug 23, 1905";
        } else if (locale.equals(Locale.FRENCH)) {
            return "les 1 janv. 2018, 1 juin 1973 et 23 août 1905";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am 01.01.2018, 01.06.1973 und 23.08.1905";
        } else {
            return "op 1 jan 2018, 1 jun 1973 en 23 aug 1905";
        }
    }
}
