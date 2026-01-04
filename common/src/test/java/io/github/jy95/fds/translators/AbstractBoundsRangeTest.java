package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractBoundsRangeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {


    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothLowAndHighWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateBothLowAndHighWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothLowAndHighWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateBothLowAndHighWithUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyHighWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyHighWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyHighWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText4(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyHighWithUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyLowWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText5(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyLowWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyLowWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText6(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateOnlyLowWithUnit();

    // For the parametrized test of first test
    private static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für zwischen 1 und 3";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por 1 a 3";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende tussen 1 en 3";
        }
else {
            return "gedurende tussen 1 en 3";
        }
    }

    // For the parametrized test of second test
    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 1 to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 1 à 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für zwischen 1 und 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por 1 a 3 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende tussen 1 en 3";
        }
else {
            return "gedurende tussen 1 en 3 dagen";
        }
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for up to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 maximum";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für bis 3";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por hasta 3";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende tot 3";
        }
else {
            return "gedurende tot 3";
        }
    }

    // For the parametrized test of fourth test
    private String getExpectedText4(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for up to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 jours maximum";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für bis 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por hasta 3 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende tot 3 dagen";
        }
else {
            return "gedurende tot 3 dagen";
        }
    }

    // For the parametrized test of fifth test
    private String getExpectedText5(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for at least 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour au moins 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für mindestens 3";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por al menos 3";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende menstens 3";
        }
else {
            return "gedurende minstens 3";
        }
    }

    // For the parametrized test of sixth test
    private String getExpectedText6(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for at least 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour au moins 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für mindestens 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por al menos 3 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "gedurende menstens 3 dagen";
        }
else {
            return "gedurende minstens 3 dagen";
        }
    }
}