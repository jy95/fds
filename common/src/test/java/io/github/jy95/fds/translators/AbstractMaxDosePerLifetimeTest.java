package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractMaxDosePerLifetimeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerLifetime(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_LIFETIME);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerLifetime(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMaxDosePerLifetime();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_LIFETIME);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateWithMaxDosePerLifetime();

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to a maximum of 50 mg for the lifetime of the patient";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’à un maximum de 50 mg pour la durée de vie du patient";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu einer maximalen Menge von 50 mg über die Lebenszeit des Patienten";
        } else {
            return "tot een maximum van 50 mg gedurende de levensduur van de patiënt";
        }
    }
}
