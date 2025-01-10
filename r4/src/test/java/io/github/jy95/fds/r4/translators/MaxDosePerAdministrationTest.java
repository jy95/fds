package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDosePerAdministrationTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerAdministration(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_ADMINISTRATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerAdministration(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Quantity quantity = new Quantity();
        quantity.setValue(50);
        quantity.setUnit("mg");
        dosage.setMaxDosePerAdministration(quantity);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.MAX_DOSE_PER_ADMINISTRATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to a maximum of 50 mg per dose";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’à un maximum de 50 mg par dose";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu einer maximalen Menge von 50 mg pro Dosis";
        } else {
            return "tot een maximum van 50 mg per dosis";
        }
    }
}
