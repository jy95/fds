package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractCountCountMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoCount(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithCountOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithCountOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothCount(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothCount();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithBothCount();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "take 2 times";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "prendere 2 volte";
        } else if (locale.equals(Locale.FRENCH)) {
            return "2 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "2 Mal nehmen";
        } else {
            return "2 keer nemen";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "take 2 to 3 times";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "prendere 2 a 3 volte";
        } else if (locale.equals(Locale.FRENCH)) {
            return "2 à 3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "von 2 bis 3 Mal nehmen";
        } else {
            return "2 tot 3 keer nemen";
        }
    }
}
