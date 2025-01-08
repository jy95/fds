package jy95.fhir.r4.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimingEventTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoEvent(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingle(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.setEvent(
                List.of(
                        new DateTimeType("2024-01-01")
                )
        );
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedSingleDayText(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultiple(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.setEvent(
                // As explained on https://build.fhir.org/datatypes.html#dateTime
                List.of(
                        new DateTimeType("2018"),
                        new DateTimeType("1973-06"),
                        new DateTimeType("1905-08-23")
                )
        );
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.TIMING_EVENT);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedMultipleDaysText(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of single form
    private static String getExpectedSingleDayText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Jan 1, 2024";
        } else if (locale.equals(Locale.FRENCH)) {
            return "le 1 janv. 2024";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am 01.01.2024";
        } else {
            return "op 1 jan 2024";
        }
    }

    // For the parametrized test of multiple form
    private String getExpectedMultipleDaysText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Jan 1, 2018, Jun 1, 1973 and Aug 23, 1905";
        } else if (locale.equals(Locale.FRENCH)) {
            return "les 1 janv. 2018, 1 juin 1973 et 23 août 1905";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am 01.01.2018, 01.06.1973 und 23.08.1905";
        } else {
            return "op 1 jan 2018, 1 jun 1973 en 23 aug 1905";
        }
    }

}
