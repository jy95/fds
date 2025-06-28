package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractRateRangeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleRateRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSimpleRateRange();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateSimpleRateRange();

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 1 to 3";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "a tasso da 1 a 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von zwischen 1 und 3";
        } else {
            return "met een verhouding van tussen 1 en 3";
        }
    }
}
