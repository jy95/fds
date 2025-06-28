package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractMaxDosePerAdministrationTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerAdministration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_ADMINISTRATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerAdministration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMaxDosePerAdministration();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_ADMINISTRATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateWithMaxDosePerAdministration();

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to a maximum of 50 mg per dose";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "fino a un massimo di 50 mg per dose";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’à un maximum de 50 mg par dose";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu einer maximalen Menge von 50 mg pro Dosis";
        } else {
            return "tot een maximum van 50 mg per dosis";
        }
    }
}
