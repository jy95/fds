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
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText1(locale), result);
    }

    public abstract D generateAsNeededBoolean();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testAsNeededCodeableConcept(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.AS_NEEDED);
        var dosage = generateAsNeededCodeableConcept();
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals(getExpectedText2(locale), result);
    }

    public abstract D generateAsNeededCodeableConcept();

    /**
     * Expected text for boolean 'as needed' flag
     */
    public static String getExpectedText1(Locale locale) {
        return switch (locale.getLanguage()) {
            case "fr" -> "si nécessaire";
            case "de" -> "bei Bedarf";
            case "es" -> "según sea necesario";
            case "it" -> "se necessario";
            case "nl" -> "indien nodig";
            default   -> "as required";
        };
    }

    /**
     * Expected text for 'as needed' with a specific reason
     */
    public String getExpectedText2(Locale locale) {
        return switch (locale.getLanguage()) {
            case "fr" -> "si nécessaire pour head pain";
            case "de" -> "bei Bedarf für head pain";
            case "es" -> "según sea necesario para head pain";
            case "it" -> "se necessario per head pain";
            case "nl" -> "zoals nodig voor head pain";
            default   -> "as required for head pain";
        };
    }
}