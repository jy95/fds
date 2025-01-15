package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTimingCodeTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoCode(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeText(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithTimingCodeText();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Take medication in the morning on weekends and days off work", result);
    }

    protected abstract D generateWithTimingCodeText();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithTimingCodeCodeAndDisplay();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Two times a day at institution specified time", result);
    }

    protected abstract D generateWithTimingCodeCodeAndDisplay();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithTimingCodeCodeOnly();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("BID", result);
    }

    protected abstract D generateWithTimingCodeCodeOnly();

}
