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
        String result = sanitize(dosageUtils.asHumanReadableText(dosage).get());
        String expected = sanitize(expectedSingleDayText(locale));
        assertEquals(expected, result);
    }

    protected abstract D generateSingle();

    // For the parametrized test of single form
    private static String expectedSingleDayText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "le 1 janv. 2024";
            case "de"    -> "am 01.01.2024";
            case "es"    -> "el 1 ene 2024";
            case "it"    -> "su 1 gen 2024";
            case "nl-BE" -> "op 1 jan. 2024";
            case "pt" -> "em 1 de janeiro de 2024";
            default      -> "on Jan 1, 2024";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultiple(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateMultiple();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_EVENT);
        String result = sanitize(dosageUtils.asHumanReadableText(dosage).get());
        String expected = sanitize(multipleDaysText(locale));
        assertEquals(expected, result);
    }

    protected abstract D generateMultiple();

    // For the parametrized test of multiple form
    private static String multipleDaysText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "les 1 janv. 2018, 1 juin 1973 et 23 août 1905";
            case "de"    -> "am 01.01.2018, 01.06.1973 und 23.08.1905";
            case "es"    -> "los 1 ene 2018, 1 jun 1973 y 23 ago 1905";
            case "it"    -> "su 1 gen 2018, 1 giu 1973 e 23 ago 1905";
            case "nl-BE" -> "op 1 jan. 2018, 1 jun. 1973 en 23 aug. 1905";
            case "pt" -> "em 1 de janeiro de 2018, 1 de junho de 1973 e 23 de agosto de 1905";
            default      -> "on Jan 1, 2018, Jun 1, 1973 and Aug 23, 1905";
        };
    }

    /**
     * Normalizes strings to prevent test failures caused by shifting Unicode (CLDR) standards.
     * It removes unstable punctuation and unifies space characters (like the Narrow No-Break Space).
     */
    private static String sanitize(String input) {
        if (input == null) return "";
        
        return input
            // Replace Narrow No-Break Space (\u202F) and Non-Breaking Space (\u00A0) with standard space
            .replace('\u202F', ' ')
            .replace('\u00A0', ' ')
            
            // Remove periods and commas which vary across CLDR versions (e.g., "Jan." vs "Jan")
            .replace(".", "")
            .replace(",", "")
            
            // Normalize typographic apostrophes to standard straight ones
            .replace("’", "'")
            
            // Collapse multiple spaces into one and trim
            .replaceAll("\\s+", " ")
            .trim();
    }
}
