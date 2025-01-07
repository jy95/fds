package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Ratio;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxDosePerPeriodTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoMaxDosePerPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.MAX_DOSE_PER_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithMaxDosePerPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(1);
        denominator.setCode("d");
        denominator.setSystem("http://hl7.org/fhir/ValueSet/units-of-time");
        ratio.setDenominator(denominator);
        dosage.setMaxDosePerPeriod(ratio);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.MAX_DOSE_PER_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to a maximum of 10 mg per day";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’à un maximum de 10 mg par jour";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu einer maximalen Menge von 10 mg pro Tag";
        } else {
            return "tot een maximum van 10 mg per dag";
        }
    }
}
