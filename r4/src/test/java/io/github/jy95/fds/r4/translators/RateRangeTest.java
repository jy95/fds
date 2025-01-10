package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Range;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateRangeTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateRange(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.RATE_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleRateRange(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.RATE_RANGE);
        Dosage dosage = new Dosage();
        Range range1 = new Range();
        range1.setLow(new Quantity(1));
        range1.setHigh(new Quantity(3));
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(range1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von zwischen 1 und 3";
        } else {
            return "met een verhouding van tussen 1 en 3";
        }
    }
}
