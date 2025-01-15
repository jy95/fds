package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractRouteTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRoute(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteText(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithRouteText();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    protected abstract D generateWithRouteText();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithRouteCodeAndDisplay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Oral route", result);
    }

    protected abstract D generateWithRouteCodeAndDisplay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithRouteCodeOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("26643006", result);
    }

    protected abstract D generateWithRouteCodeOnly();
}
