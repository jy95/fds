package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractAsNeededTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoAsNeeded(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.AS_NEEDED);
        var dosage = generateEmptyDosage();
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testAsNeededBoolean(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.AS_NEEDED);
        var dosage = generateAsNeededBoolean();
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    public abstract D generateAsNeededBoolean();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testAsNeededCodeableConcept(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.AS_NEEDED);
        var dosage = generateAsNeededCodeableConcept();
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    public abstract D generateAsNeededCodeableConcept();

    // For the parametrized test of single form
    public static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "as required";
        } else if (locale.equals(Locale.FRENCH)) {
            return "si nécessaire";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bei Bedarf";
        } else {
            return "indien nodig";
        }
    }

    // For the parametrized test of multiple form
    public String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "as required for head pain";
        }
        else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "como se requiere para el dolor de cabeza";
        } else if (locale.equals(Locale.FRENCH)) {
            return "si nécessaire pour head pain";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bei Bedarf für head pain";
        } else {
            return "zoals nodig voor head pain";
        }
    }
}
