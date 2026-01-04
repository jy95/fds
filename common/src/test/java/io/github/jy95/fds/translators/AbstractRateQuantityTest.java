package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractRateQuantityTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateQuantity(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleRateQuantity(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSimpleRateQuantity();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.RATE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    protected abstract D generateSimpleRateQuantity();

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 5 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 5 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 5 ml";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "a una tasa de 5 ml";
        }         else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "met een verhouding van 5 ml";
        }
else {
            return "met een verhouding van 5 ml";
        }
    }
}
