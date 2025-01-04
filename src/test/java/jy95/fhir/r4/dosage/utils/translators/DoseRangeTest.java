package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoseRangeTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDoseRange(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleDoseRange(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_RANGE);
        Dosage dosage = new Dosage();
        Range range1 = new Range();
        range1.setLow(new Quantity(1));
        range1.setHigh(new Quantity(3));
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(range1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "zwischen 1 und 3";
        } else {
            return "tussen 1 en 3";
        }
    }

}
