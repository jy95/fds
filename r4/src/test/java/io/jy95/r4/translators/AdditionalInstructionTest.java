package io.github.jy95.r4.translators;

import io.github.jy95.r4.DosageAPIR4;
import io.github.jy95.r4.AbstractFhirTest;
import io.github.jy95.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdditionalInstructionTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Instruction 1");
        dosage.setAdditionalInstruction(List.of(cc1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Instruction 1", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleAdditionalInstruction(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.ADDITIONAL_INSTRUCTION);
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Instruction 1");
        CodeableConcept cc2 = new CodeableConcept();
        cc2.setText("Instruction 2");
        dosage.setAdditionalInstruction(List.of(cc1, cc2));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedMultipleText(locale);
        assertEquals(expectedResult, result);
    }

    // For the parametrized test of multiple form
    private String getExpectedMultipleText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "Instruction 1 and Instruction 2";
        } else if (locale.equals(Locale.FRENCH)) {
            return "Instruction 1 et Instruction 2";
        } else if (locale.equals(Locale.GERMAN)) {
            return "Instruction 1 und Instruction 2";
        } else {
            return "Instruction 1 en Instruction 2";
        }
    }

}
