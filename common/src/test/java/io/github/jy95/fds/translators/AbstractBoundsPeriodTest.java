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

public abstract class AbstractBoundsPeriodTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothStartAndEnd(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothStartAndEnd();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateBothStartAndEnd();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyEnd(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyEnd();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertExpectedText2(locale, result);
    }

    protected abstract D generateOnlyEnd();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyStart(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyStart();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyStart();

    // For the parametrized test of first test
    private static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "from May 23, 2011 to May 27, 2011";
        } else if (locale.equals(Locale.FRENCH)) {
            return "du 23 mai 2011 au 27 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "von 23.05.2011 bis 27.05.2011";
        } else {
            return "van 23 mei 2011 tot 27 mei 2011";
        }
    }

    // For the parametrized test of second test
    private void assertExpectedText2(Locale locale, String actual) {
        if (locale.equals(Locale.ENGLISH)) {
            // Check across several JDK requires splitting assertions to make them work
            assertTrue(actual.startsWith("to Feb 7, 2015"));
            assertTrue(actual.contains("1:28:17"));
            assertTrue(actual.endsWith("PM"));
        } else if (locale.equals(Locale.FRENCH)) {
            // Check across several JDK requires splitting assertions to make them work
            assertTrue(actual.startsWith("jusqu’au 7 févr. 2015"));
            assertTrue(actual.endsWith("13:28:17"));
        } else if (locale.equals(Locale.GERMAN)) {
            assertEquals("bis 07.02.2015, 13:28:17", actual);
        } else {
            // Check across several JDK requires splitting assertions to make them work
            assertTrue(actual.startsWith("tot 7 feb 2015"));
            assertTrue(actual.contains("13:28:17"));
        }
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "from May 23, 2011";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à partir du 23 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "ab 23.05.2011";
        } else {
            return "van 23 mei 2011";
        }
    }
}
