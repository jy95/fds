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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithPeriodOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithPeriodOnly();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "chaque 2 jours";
            case "de"    -> "alle 2 Tage";
            case "es"    -> "cada 2 días";
            case "it"    -> "ogni 2 giorni";
            case "nl-BE" -> "per 2 dagen";
            case "pt" -> "a cada 2 dias";
            default      -> "every 2 days";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothPeriod();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithBothPeriod();

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "chaque 2 à 3 jours";
            case "de"    -> "jede 2 bis zu 3 Tage";
            case "es"    -> "cada 2 a 3 días";
            case "it"    -> "ogni 2 a 3 giorni";
            case "nl-BE" -> "elke 2 tot 3 dagen";
            case "pt" -> "a cada 2 a 3 dias";
            default      -> "every 2 to 3 days";
        };
    }
}