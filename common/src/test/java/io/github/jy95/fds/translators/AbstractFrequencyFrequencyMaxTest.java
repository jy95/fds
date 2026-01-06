package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFrequencyFrequencyMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequency(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithFrequencyOnly();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "3 fois";
            case "de"    -> "3 Mal";
            case "es"    -> "3 veces";
            case "it"    -> "3 volte";
            case "nl-BE" -> "3 keer";
            default      -> "3 times";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyMaxOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyMaxOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithFrequencyMaxOnly();

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "jusqu'à 3 fois";
            case "de"    -> "bis zu 3 Mal";
            case "es"    -> "hasta 3 veces";
            case "it"    -> "fino a 3 volte";
            case "nl-BE" -> "tot 3 keer";
            default      -> "up to 3 times";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothFrequency(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothFrequency();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateWithBothFrequency();

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "3-5 fois";
            case "de"    -> "3-5 Mal";
            case "es"    -> "3-5 veces";
            case "it"    -> "3-5 volte";
            case "nl-BE" -> "3-5 keer";
            default      -> "3-5 times";
        };
    }
}