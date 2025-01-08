package jy95.fhir.r4.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsNeededTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoAsNeeded(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.AS_NEEDED);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testAsNeededBoolean(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        BooleanType flag = new BooleanType();
        flag.setValue(Boolean.TRUE);
        dosage.setAsNeeded(flag);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.AS_NEEDED);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testAsNeededCodeableConcept(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept neededFor = new CodeableConcept();
        neededFor.setText("head pain");
        dosage.setAsNeeded(neededFor);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.AS_NEEDED);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of single form
    private static String getExpectedText1(Locale locale) {
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
    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "as required for head pain";
        } else if (locale.equals(Locale.FRENCH)) {
            return "si nécessaire pour head pain";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bei Bedarf für head pain";
        } else {
            return "zoals nodig voor head pain";
        }
    }
}
