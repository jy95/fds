package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractBoundsDurationTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_DURATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBoundsDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBoundsDuration();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_DURATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateWithBoundsDuration();

    // For the parametrized test of first test
    private static String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für 3 Tage";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por 3 días";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "per 3 giorni";
        }
else {
            return "gedurende 3 dagen";
        }
    }
}