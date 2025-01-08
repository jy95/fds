package jy95.fhir.r4.translators;

import jy95.fhir.r4.DosageAPIR4;
import jy95.fhir.r4.AbstractFhirTest;
import jy95.fhir.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MethodTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMethod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodText(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("With or after food");
        dosage.setMethod(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/administration-method-codes", "738996007", "Spray")
                )
        );
        dosage.setMethod(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Spray", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMethodCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/administration-method-codes", "738996007", null)
                )
        );
        dosage.setMethod(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.METHOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("738996007", result);
    }

}
