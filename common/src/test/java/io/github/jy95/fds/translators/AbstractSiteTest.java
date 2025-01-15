package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractSiteTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoSite(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteText(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithSiteText();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    protected abstract D generateWithSiteText();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithSiteCodeAndDisplay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Entire ear", result);
    }

    protected abstract D generateWithSiteCodeAndDisplay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithSiteCodeOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("1910005", result);
    }

    protected abstract D generateWithSiteCodeOnly();

}
