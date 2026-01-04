package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractPeriodPeriodMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithPeriodOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithPeriodOnly();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "every 2 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "chaque 2 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "alle 2 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "cada 2 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "per 2 dagen";
        }
else {
            return "per 2 dagen";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothPeriod();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithBothPeriod();

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "every 2 to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "chaque 2 à 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "jede 2 bis zu 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "cada 2 a 3 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "Elke 2 tot 3 dagen";
        }
else {
            return "elke 2 tot 3 dagen";
        }
    }
}