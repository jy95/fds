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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithCountOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithCountOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothCount(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothCount();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithBothCount();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "prendre 2 fois";
            case "de"    -> "2 Mal nehmen";
            case "es"    -> "tomar 2 veces";
            case "it"    -> "prendere 2 volte";
            case "nl-BE" -> "2 keer nemen";
            case "pt" -> "tomar 2 vezes";
                        default      -> "take 2 times";
        };
    }

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "prendre 2 à 3 fois";
            case "de"    -> "von 2 bis 3 Mal nehmen";
            case "es"    -> "tomar de 2 a 3 veces";
            case "it"    -> "prendere 2 a 3 volte";
            case "nl-BE" -> "2 tot 3 keer nemen";
            case "pt" -> "tomar 2 a 3 vezes";
                        default      -> "take 2 to 3 times";
        };
    }
}