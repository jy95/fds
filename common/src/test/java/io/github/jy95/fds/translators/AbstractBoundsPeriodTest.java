package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
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
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "du 23 mai 2011 au 27 mai 2011";
            case "de"    -> "von 23.05.2011 bis 27.05.2011";
            case "es"    -> "desde 23 may 2011 hasta 27 may 2011";
            case "it"    -> "da 23 mag 2011 a 27 mag 2011";
            case "nl-BE" -> "van 23 mei 2011 tot 27 mei 2011";
            default      -> "from May 23, 2011 to May 27, 2011";
        };
    }

    // For the parametrized test of second test
    private static String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "jusqu’au 7 févr. 2015 à 13:28:17";
            case "de"    -> "bis 07.02.2015, 13:28:17";
            case "es"    -> "hasta 7 feb 2015 a las 13:28:17";
            case "it"    -> "a 7 feb 2015, 13:28:17";
            case "nl-BE" -> "tot 7 feb 2015 om 13:28:17";
            default      -> "to Feb 7, 2015 at 1:28:17 PM";
        };
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "à partir du 23 mai 2011";
            case "de"    -> "ab 23.05.2011";
            case "es"    -> "desde 23 may 2011";
            case "it"    -> "da 23 mag 2011";
            case "nl-BE" -> "van 23 mei 2011";
            default      -> "from May 23, 2011";
        };
    }
}
