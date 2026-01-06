package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTimeOfDayTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSingleTimeOfDay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedSingleTimeText(locale), result);
    }

    protected abstract D generateSingleTimeOfDay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultipleTimeOfDay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIME_OF_DAY);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedMultipleTimesText(locale), result);
    }

    protected abstract D generateMultipleTimeOfDay();

    // Expected text for single timeOfDay
    private static String getExpectedSingleTimeText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "à 15:00";
            case "de"    -> "um 15:00";
            case "es"    -> "a las 15:00";
            case "it"    -> "a 15:00";
            case "nl-BE" -> "om 15:00";
            case "pt" -> "às 15:00";
                        default      -> "at 15:00";
        };
    }

    // Expected text for multiple timeOfDay
    private static String getExpectedMultipleTimesText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "à 15:00 et 12:12:12";
            case "de"    -> "um 15:00 und 12:12:12";
            case "es"    -> "a las 15:00 y 12:12:12";
            case "it"    -> "a 15:00 e 12:12:12";
            case "nl-BE" -> "om 15:00 en 12:12:12";
            case "pt" -> "às 15:00 e 12:12:12";
                        default      -> "at 15:00 and 12:12:12";
        };
    }
}