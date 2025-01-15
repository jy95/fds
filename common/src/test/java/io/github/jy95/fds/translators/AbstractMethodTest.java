package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractMethodTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMethod(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodText(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMethodText();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    protected abstract D generateWithMethodText();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMethodCodeAndDisplay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Spray", result);
    }

    protected abstract D generateWithMethodCodeAndDisplay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithMethodCodeOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("738996007", result);
    }

    protected abstract D generateWithMethodCodeOnly();
}
