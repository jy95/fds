package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractDurationDurationMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithDurationOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithDurationOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0"," ");
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithDurationOnly();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "over 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "durant 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "sobre 3 días";
        } else {
            return "gedurende 3 dagen";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithDurationMaxOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithDurationMaxOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0"," ");
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithDurationMaxOnly();

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "maximum 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "maximum 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "maximal 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "máximo 3 días";
        } else {
            return "maximaal 3 dagen";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothDuration();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0"," ");
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithBothDuration();

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "over 3 days ( maximum 5 days )";
        } else if (locale.equals(Locale.FRENCH)) {
            return "durant 3 jours ( maximum 5 jours )";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für 3 Tage ( maximal 5 Tage )";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "sobre 3 días ( máximo 5 días )";
        } else {
            return "gedurende 3 dagen ( maximaal 5 dagen )";
        }
    }
}