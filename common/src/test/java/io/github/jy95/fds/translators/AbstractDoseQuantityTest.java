package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractDoseQuantityTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDoseQuantity(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleDoseQuantity(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateSimpleDoseQuantity();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("5 ml", result);
    }

    protected abstract D generateSimpleDoseQuantity();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityWithComparator(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateDoseQuantityWithComparator();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("< 5 ml", result);
    }

    protected abstract D generateDoseQuantityWithComparator();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateDoseQuantityWithoutUnit();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.DOSE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("5", result);
    }

    protected abstract D generateDoseQuantityWithoutUnit();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityCustom(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateDoseQuantityCustom();
        var dosageUtils = getDosageAPI(generateCustomConfig());
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("8 ml", result);
    }

    protected abstract D generateDoseQuantityCustom();

    protected abstract C generateCustomConfig();

}
