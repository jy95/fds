package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundsPeriodTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothStartAndEnd(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setStartElement(new DateTimeType("2011-05-23"));
        boundsPeriod.setEndElement(new DateTimeType("2011-05-27"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyEnd(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setEndElement(new DateTimeType("2015-02-07T13:28:17"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertExpectedText2(locale, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyStart(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Period boundsPeriod = new Period();
        boundsPeriod.setStartElement(new DateTimeType("2011-05-23"));
        timingRepeatComponent.setBounds(boundsPeriod);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_PERIOD);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of first test
    private static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "from May 23, 2011 to May 27, 2011";
        } else if (locale.equals(Locale.FRENCH)) {
            return "du 23 mai 2011 au 27 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "von 23.05.2011 bis 27.05.2011";
        } else {
            return "van 23 mei 2011 tot 27 mei 2011";
        }
    }

    // For the parametrized test of second test
    private void assertExpectedText2(Locale locale, String actual) {
        if (locale.equals(Locale.ENGLISH)) {
            assertEquals("to Feb 7, 2015, 1:28:17 PM", actual);
        } else if (locale.equals(Locale.FRENCH)) {
            assertEquals("jusqu’au 7 févr. 2015, 13:28:17", actual);
        } else if (locale.equals(Locale.GERMAN)) {
            assertEquals("bis 07.02.2015, 13:28:17", actual);
        } else {
            // Check for both acceptable Dutch formats
            assertEquals("tot 7 feb 2015, 13:28:17", actual);
        }
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "from May 23, 2011";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à partir du 23 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "ab 23.05.2011";
        } else {
            return "van 23 mei 2011";
        }
    }
}
