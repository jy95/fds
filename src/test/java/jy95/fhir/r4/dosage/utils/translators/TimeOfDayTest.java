package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class TimeOfDayTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = TimeOfDayTest.getDosageUtilsInstance(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.getRepeat().addTimeOfDay("15:00:00");
        dosage.setTiming(timing);

        FhirDosageUtils dosageUtils = TimeOfDayTest.getDosageUtilsInstance(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedSingleTimeText(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleTimeOfDay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.getRepeat().addTimeOfDay("15:00:00");
        timing.getRepeat().addTimeOfDay("12:12:12");
        dosage.setTiming(timing);

        FhirDosageUtils dosageUtils = TimeOfDayTest.getDosageUtilsInstance(locale, DisplayOrder.TIME_OF_DAY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedMultipleTimesText(locale);
        assertEquals(expectedResult, result);
    }

    // Expected text for single timeOfDay
    private static String getExpectedSingleTimeText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at 15:00";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à 15:00";
        } else if (locale.equals(Locale.GERMAN)) {
            return "um 15:00";
        } else {
            return "om 15:00";
        }
    }

    // Expected text for multiple timeOfDay
    private static String getExpectedMultipleTimesText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at 15:00 and 12:12:12";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à 15:00 et 12:12:12";
        } else if (locale.equals(Locale.GERMAN)) {
            return "um 15:00 und 12:12:12";
        } else {
            return "om 15:00 en 12:12:12";
        }
    }

}
