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
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithWhenOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithWhenOnly();

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "during the morning and during the night";
        } else if (locale.equals(Locale.FRENCH)) {
            return "durant le matin et durant la nuit";
        } else if (locale.equals(Locale.GERMAN)) {
            return "während des Vormittags und über Nacht";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "durante la mañana y durante la noche";
        }         else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "tijdens de ochtend en tijdens de nacht";
        }
else {
            return "tijdens de ochtend en tijdens de nacht";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenAndCount(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithWhenAndCount();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        String result = dosageUtils.asHumanReadableText(dosage).get().replace("\u00a0"," ");
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithWhenAndCount();

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 hour and 30 minutes during the morning and during the night";
        } else if (locale.equals(Locale.FRENCH)) {
            return "1 heure et 30 minutes durant le matin et durant la nuit";
        } else if (locale.equals(Locale.GERMAN)) {
            return "1 Stunde und 30 Minuten während des Vormittags und über Nacht";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "1 hora y 30 minutos durante la mañana y durante la noche";
        }         else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "1 uur en 30 minuten tijdens de ochtend en tijdens de nacht";
        }
else {
            return "1 uur en 30 minuten tijdens de ochtend en tijdens de nacht";
        }
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithCountOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    protected abstract D generateWithCountOnly();

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "30 minutes";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "30 minuti";
        } else if (locale.equals(Locale.FRENCH)) {
            return "30 minutes";
        } else if (locale.equals(Locale.GERMAN)) {
            return "30 Minuten";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "30 minutos";
        }         else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "30 minuti";
        }
else {
            return "30 minuten";
        }
    }
}