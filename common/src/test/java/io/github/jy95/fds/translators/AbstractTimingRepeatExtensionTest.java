package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractTimingRepeatExtensionTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoExtension(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateEmptyDosage();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_REPEAT_EXTENSION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithExtension(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithExtension();
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_REPEAT_EXTENSION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("[{\"url\":\"http://hl7.org/fhir/StructureDefinition/timing-exact\",\"value[x]\":\"true\"}]", result);
    }

    protected abstract D generateWithExtension();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithExtensionCustom(Locale locale) throws ExecutionException, InterruptedException {
        var dosage = generateWithExtensionCustom();
        var config = generateCustomConfig(locale);
        var dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_REPEAT_EXTENSION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("(exact timing)", result);
    }

    protected abstract D generateWithExtensionCustom();

    protected abstract C generateCustomConfig(Locale locale);
}
