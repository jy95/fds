package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
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
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
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
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
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
        boundsRange.setHigh(new Quantity(null, 3, "http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
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
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyHighWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setHigh(new Quantity(null, 3,"http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText4(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(3));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText5(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyLowWithUnit(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        Range boundsRange = new Range();
        boundsRange.setLow(new Quantity(null, 3, "http://hl7.org/fhir/ValueSet/duration-units", "d", null));
        timingRepeatComponent.setBounds(boundsRange);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.BOUNDS_RANGE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText6(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of first test
    private static String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 1 to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 1 à 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für zwischen 1 und 3";
        } else {
            return "gedurende tussen 1 en 3";
        }
    }

    // For the parametrized test of second test
    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 1 to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 1 à 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für zwischen 1 und 3 Tage";
        } else {
            return "gedurende tussen 1 en 3 dagen";
        }
    }

    // For the parametrized test of third test
    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for up to 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 maximum";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für bis 3";
        } else {
            return "gedurende tot 3";
        }
    }

    // For the parametrized test of four test
    private String getExpectedText4(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for up to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 jours maximum";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für bis 3 Tage";
        } else {
            return "gedurende tot 3 dagen";
        }
    }

    // For the parametrized test of fifth test
    private String getExpectedText5(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for at least 3";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour au moins 3";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für mindestens 3";
        } else {
            return "gedurende minstens 3";
        }
    }

    // For the parametrized test of sixth test
    private String getExpectedText6(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for at least 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour au moins 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für mindestens 3 Tage";
        } else {
            return "gedurende minstens 3 dagen";
        }
    }
}
