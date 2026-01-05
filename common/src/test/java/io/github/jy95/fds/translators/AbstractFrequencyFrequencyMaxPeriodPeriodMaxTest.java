package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFrequencyFrequencyMaxPeriodPeriodMaxTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequencyOrPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithFrequencyOnly();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithFrequencyOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithPeriodOnly();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithPeriodOnly();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothFrequencyAndPeriod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothFrequencyAndPeriod();
        var dosageUtils = getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateBothFrequencyAndPeriod();

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

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "chaque 2 jours";
            case "de"    -> "alle 2 Tage";
            case "es"    -> "cada 2 días";
            case "it"    -> "ogni 2 giorni";
            case "nl-BE" -> "per 2 dagen";
            default      -> "every 2 days";
        };
    }

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "3 fois chaque 2 jours";
            case "de"    -> "3 Mal alle 2 Tage";
            case "es"    -> "3 veces cada 2 días";
            case "it"    -> "3 volte ogni 2 giorni";
            case "nl-BE" -> "3 keer per 2 dagen";
            default      -> "3 times every 2 days";
        };
    }
}