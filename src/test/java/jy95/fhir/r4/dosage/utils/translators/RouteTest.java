package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRoute(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteText(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("With or after food");
        dosage.setRoute(cc1);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/route-codes", "26643006", "Oral route")
                )
        );
        dosage.setRoute(cc1);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Oral route", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithRouteCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/route-codes", "26643006", null)
                )
        );
        dosage.setRoute(cc1);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.ROUTE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("26643006", result);
    }
}
