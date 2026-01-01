package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertExpectedSingleDayText(locale, result);
    }

    protected abstract D generateSingle();

    // For the parametrized test of single form
    private static void assertExpectedSingleDayText(Locale locale, String actual) {
        if (locale.equals(Locale.ENGLISH)) {
            assertEquals( "on Jan 1, 2024", actual);
        } else if (locale.equals(Locale.FRENCH)) {
            assertEquals("le 1 janv. 2024",actual);
        } else if (locale.equals(Locale.GERMAN)) {
            assertEquals("am 01.01.2024", actual);
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            assertEquals("el 1 ene 2024", actual);
        } else {
            // Check across several JDK requires splitting assertions to make them work
            assertTrue(actual.startsWith("op 1 jan"));
            assertTrue(actual.endsWith("2024"));
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultiple(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultiple();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertMultipleDaysText(locale, result);
    }

    protected abstract D generateMultiple();

    // For the parametrized test of multiple form
    private void assertMultipleDaysText(Locale locale, String actual) {
        if (locale.equals(Locale.ENGLISH)) {
            assertEquals("on Jan 1, 2018, Jun 1, 1973 and Aug 23, 1905", actual);
        } else if (locale.equals(Locale.FRENCH)) {
            assertEquals("les 1 janv. 2018, 1 juin 1973 et 23 août 1905", actual);
        } else if (locale.equals(Locale.GERMAN)) {
            assertEquals("am 01.01.2018, 01.06.1973 und 23.08.1905", actual);
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            assertEquals("los 1 ene 2018, 1 jun 1973 y 23 ago 1905", actual);
        } else {
            assertTrue(
                    actual.equals("op 1 jan 2018, 1 jun 1973 en 23 aug 1905") ||
                            actual.equals("op 1 jan. 2018, 1 jun. 1973 en 23 aug. 1905")
            );
        }
    }
}