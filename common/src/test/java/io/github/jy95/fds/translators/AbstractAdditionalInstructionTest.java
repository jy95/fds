package io.github.jy95.fds.translators;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.utilities.AbstractTranslatorTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractAdditionalInstructionTest<C extends FDSConfig, D> extends AbstractTranslatorTest<C, D> {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        var dosage = generateEmptyDosage();
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        var dosage = getDosageWithSingleInstruction();
        var result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Instruction 1", result);
    }

    protected abstract D getDosageWithSingleInstruction();

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        var dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        var dosage = getDosageMultipleAdditionalInstruction();
        var result = dosageUtils.asHumanReadableText(dosage).get();
        var expectedResult = getExpectedMultipleAdditionalInstruction(locale);
        assertEquals(expectedResult, result);
    }

    // For multiple instructions test
    protected abstract D getDosageMultipleAdditionalInstruction();

    // Expected result
    protected String getExpectedMultipleAdditionalInstruction(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "Instruction 1 and Instruction 2";
        }
        else if (locale.equals(Locale.forLanguageTag("it"))) {
            return "Istruzione 1 e Istruzione 2";
        }
        else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "Instruction 1 y Instruction 2";
        } else if (locale.equals(Locale.FRENCH)) {
            return "Instruction 1 et Instruction 2";
        } else if (locale.equals(Locale.GERMAN)) {
            return "Instruction 1 und Instruction 2";
        } else {
            return "Instruction 1 en Instruction 2";
        }
    }
}
