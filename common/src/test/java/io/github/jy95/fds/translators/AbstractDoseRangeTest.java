package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractDoseRangeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDoseRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleDoseRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSimpleDoseRange();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateSimpleDoseRange();

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "zwischen 1 und 3";
         } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "1 a 3";
        } else if (locale.equals(Locale.ITALIAN)) {
            return "1 a 3";
        }
else {
            return "tussen 1 en 3";
        }
    }

}
