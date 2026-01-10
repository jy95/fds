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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBoundsDuration(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithBoundsDuration();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_DURATION);
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText(locale), result);
    }

    protected abstract D generateWithBoundsDuration();

    private static String getExpectedText(Locale locale) {
        return switch (locale.toLanguageTag()) {
            case "fr" -> "pour 3 jours";
            case "de" -> "für 3 Tage";
            case "es" -> "por 3 días";
            case "it" -> "per 3 giorni";
            case "nl-BE" -> "gedurende 3 dagen";
            case "pt" -> "por 3 dias";
            default   -> "for 3 days";
        };
    }
}