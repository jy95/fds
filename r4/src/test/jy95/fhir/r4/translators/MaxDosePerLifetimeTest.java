package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDosePerLifetimeTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerLifetime(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.MAX_DOSE_PER_LIFETIME);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerLifetime(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Quantity quantity = new Quantity();
        quantity.setValue(50);
        quantity.setUnit("mg");
        dosage.setMaxDosePerLifetime(quantity);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.MAX_DOSE_PER_LIFETIME);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to a maximum of 50 mg for the lifetime of the patient";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’à un maximum de 50 mg pour la durée de vie du patient";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu einer maximalen Menge von 50 mg über die Lebenszeit des Patienten";
        } else {
            return "tot een maximum van 50 mg gedurende de levensduur van de patiënt";
        }
    }
}
