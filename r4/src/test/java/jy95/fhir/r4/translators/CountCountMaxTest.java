package jy95.fhir.r4.translators;

import jy95.fhir.r4.DosageAPIR4;
import jy95.fhir.r4.AbstractFhirTest;
import jy95.fhir.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountCountMaxTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoCount(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setCount(2);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothCount(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.COUNT_COUNT_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setCount(2);
        repeatComponent.setCountMax(3);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "take 2 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "2 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "2 Mal nehmen";
        } else {
            return "2 keer nemen";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "take 2 to 3 times";
        } else if (locale.equals(Locale.FRENCH)) {
            return "2 à 3 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "von 2 bis 3 Mal nehmen";
        } else {
            return "2 tot 3 keer nemen";
        }
    }

}
