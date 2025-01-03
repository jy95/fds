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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrequencyFrequencyMaxTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequency(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setFrequency(3);
        
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyMaxOnly(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setFrequencyMax(3);
        
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothFrequency(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.FREQUENCY_FREQUENCY_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setFrequency(3);
        repeatComponent.setFrequencyMax(5);
        
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3 Mal";
        } else {
            return "3 keer";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "up to 3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "jusqu'à 3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis zu 3 Mal";
        } else {
            return "tot 3 keer";
        }
    }

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3-5 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3-5 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3-5 Mal";
        } else {
            return "3-5 keer";
        }
    }

}
