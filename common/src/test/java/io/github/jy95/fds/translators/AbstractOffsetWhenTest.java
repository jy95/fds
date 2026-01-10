package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractOffsetWhenTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoOffsetAndWhen(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithWhenOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    protected abstract D generateWithWhenOnly();

    private String getExpectedText1(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "durant le matin et durant la nuit";
            case "de"    -> "während des Vormittags und über Nacht";
            case "es"    -> "durante la mañana y durante la noche";
            case "it"    -> "durante la mattina e durante la notte";
            case "nl-BE" -> "tijdens de ochtend en tijdens de nacht";
            case "pt" -> "durante a manhã e durante a madrugada";
            default      -> "during the morning and during the night";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenAndCount(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithWhenAndCount();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        var result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0", " ");
        assertEquals(getExpectedText2(locale), result);
    }

    protected abstract D generateWithWhenAndCount();

    private String getExpectedText2(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "1 heure et 30 minutes durant le matin et durant la nuit";
            case "de"    -> "1 Stunde und 30 Minuten während des Vormittags und über Nacht";
            case "es"    -> "1 hora y 30 minutos durante la mañana y durante la noche";
            case "it"    -> "1 ora e 30 minuti durante la mattina e durante la notte";
            case "nl-BE" -> "1 uur en 30 minuten tijdens de ochtend en tijdens de nacht";
            case "pt" -> "1 hora e 30 minutos durante a manhã e durante a madrugada";
            default      -> "1 hour and 30 minutes during the morning and during the night";
        };
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithCountOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText3(locale), result);
    }

    protected abstract D generateWithCountOnly();

    private String getExpectedText3(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "30 minutes";
            case "de"    -> "30 Minuten";
            case "es"    -> "30 minutos";
            case "it"    -> "30 minuti";
            case "nl-BE" -> "30 minuten";
            case "pt" -> "30 minutos";
            default      -> "30 minutes";
        };
    }
}