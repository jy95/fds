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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleDoseRange(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSimpleDoseRange();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_RANGE);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText(locale), result);
    }

    protected abstract D generateSimpleDoseRange();

    private String getExpectedText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr"    -> "1 à 3";
            case "de"    -> "zwischen 1 und 3";
            case "es"    -> "1 a 3";
            case "it"    -> "1 a 3";
            case "nl-BE" -> "tussen 1 en 3";
            default      -> "1 to 3";
        };
    }
}