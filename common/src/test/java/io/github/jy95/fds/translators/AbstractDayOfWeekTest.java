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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSingleDayOfWeek();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DAY_OF_WEEK);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedSingleDayText(locale), result);
    }

    protected abstract D generateSingleDayOfWeek();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultipleDayOfWeek();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DAY_OF_WEEK);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedMultipleDaysText(locale), result);
    }

    protected abstract D generateMultipleDayOfWeek();

    private static String getExpectedSingleDayText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "le vendredi";
            case "de"    -> "am Freitag";
            case "es"    -> "el viernes";
            case "it"    -> "su venerdì";
            case "nl-BE" -> "op vrijdag";
            case "pt" -> "na sexta-feira";
            default      -> "on Friday";
        };
    }

    private String getExpectedMultipleDaysText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "les lundi et vendredi";
            case "de"    -> "am Montag und Freitag";
            case "es"    -> "los lunes y viernes";
            case "it"    -> "su lunedì e venerdì";
            case "nl-BE" -> "op maandag en vrijdag";
            case "pt" -> "na segunda-feira e sexta-feira";
            default      -> "on Monday and Friday";
        };
    }
}