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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithDurationOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithDurationOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0", " ");
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithDurationOnly();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "durant 3 jours";
            case "de"    -> "für 3 Tage";
            case "es"    -> "sobre 3 días";
            case "it"    -> "su 3 giorni";
            case "nl-BE" -> "gedurende 3 dagen";
            case "pt" -> "durante 3 dias";
            default      -> "over 3 days";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithDurationMaxOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithDurationMaxOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0", " ");
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithDurationMaxOnly();

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "maximum 3 jours";
            case "de"    -> "maximal 3 Tage";
            case "es"    -> "máximo 3 días";
            case "it"    -> "massimo 3 giorni";
            case "nl-BE" -> "maximaal 3 dagen";
            case "pt" -> "máximo 3 dias";
            default      -> "maximum 3 days";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBothDuration();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DURATION_DURATION_MAX);
        var result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0", " ");
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateWithBothDuration();

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "durant 3 jours ( maximum 5 jours )";
            case "de"    -> "für 3 Tage ( maximal 5 Tage )";
            case "es"    -> "sobre 3 días ( máximo 5 días )";
            case "it"    -> "su 3 giorni ( massimo 5 giorni )";
            case "nl-BE" -> "gedurende 3 dagen ( maximaal 5 dagen )";
            case "pt" -> "durante 3 dias ( máximo 5 dias )";
            default      -> "over 3 days ( maximum 5 days )";
        };
    }
}