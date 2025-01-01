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

public class BoundsRangeTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsRange(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(1));
        boundsRange.setHigh(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothLowAndHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(1));
        boundsRange.setHigh(new Quantity(null, 3, null, null, "ml"));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setHigh(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of first test
    private static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "du 23 mai 2011 au 27 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "zwischen 1 und 3";
        } else {
            return "tussen 1 en 3";
        }
    }

    // For the parametrized test of second test
    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 to 3 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu’au 7 févr. 2015, 13:28:17";
        } else if (locale.equals(Locale.GERMAN)) {
            return "zwischen 1 und 3 ml";
        } else {
            return "tussen 1 en 3 ml";
        }
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "à partir du 23 mai 2011";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis 3";
        } else {
            return "tot 3";
        }
    }

    // For the parametrized test of four test
    private String getExpectedText4(Locale locale) {
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

    // For the parametrized test of fifth test
    private String getExpectedText5(Locale locale) {
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

    // For the parametrized test of sixth test
    private String getExpectedText6(Locale locale) {
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
