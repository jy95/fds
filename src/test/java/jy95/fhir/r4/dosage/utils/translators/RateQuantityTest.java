package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateQuantityTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateQuantity(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleRateQuantity(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_QUANTITY);
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 5 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 5 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 5 ml";
        } else {
            return "met een verhouding van 5 ml";
        }
    }

}
