package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractBoundsRangeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothLowAndHighWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateBothLowAndHighWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateBothLowAndHighWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateBothLowAndHighWithUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyHighWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateOnlyHighWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyHighWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText4(locale), result);
    }

    protected abstract D generateOnlyHighWithUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyLowWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText5(locale), result);
    }

    protected abstract D generateOnlyLowWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateOnlyLowWithUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText6(locale), result);
    }

    protected abstract D generateOnlyLowWithUnit();

    private static String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour 1 à 3";
            case "de" -> "für zwischen 1 und 3";
            case "es" -> "por 1 a 3";
            case "it" -> "per 1 a 3";
            case "nl-BE" -> "gedurende tussen 1 en 3";
            case "pt" -> "1 a 3";
                        default -> "for 1 to 3";
        };
    }

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour 1 à 3 jours";
            case "de" -> "für zwischen 1 und 3 Tage";
            case "es" -> "por 1 a 3 días";
            case "it" -> "per 1 a 3 giorni";
            case "nl-BE" -> "gedurende tussen 1 en 3 dagen";
            case "pt" -> "durante 1 a 3 dias";
                        default -> "for 1 to 3 days";
        };
    }

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour 3 maximum";
            case "de" -> "für bis 3";
            case "es" -> "por hasta 3";
            case "it" -> "per fino a 3";
            case "nl-BE" -> "gedurende tot 3";
            case "pt" -> "até 3";
                        default -> "for up to 3";
        };
    }

    private String getExpectedText4(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour 3 jours maximum";
            case "de" -> "für bis 3 Tage";
            case "es" -> "por hasta 3 días";
            case "it" -> "per fino a 3 giorni";
            case "nl-BE" -> "gedurende tot 3 dagen";
            case "pt" -> "até 3 dias";
                        default -> "for up to 3 days";
        };
    }

    private String getExpectedText5(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour au moins 3";
            case "de" -> "für mindestens 3";
            case "es" -> "por al menos 3";
            case "it" -> "per almeno 3";
            case "nl-BE" -> "gedurende minstens 3";
            case "pt" -> "para pelo menos 3";
                        default -> "for at least 3";
        };
    }

    private String getExpectedText6(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour au moins 3 jours";
            case "de" -> "für mindestens 3 Tage";
            case "es" -> "por al menos 3 días";
            case "it" -> "per almeno 3 giorni";
            case "nl-BE" -> "gedurende minstens 3 dagen";
            case "pt" -> "durante pelo menos 3 dias";
                        default -> "for at least 3 days";
        };
    }
}