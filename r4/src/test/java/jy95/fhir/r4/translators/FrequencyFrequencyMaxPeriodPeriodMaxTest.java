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

public class FrequencyFrequencyMaxPeriodPeriodMaxTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoFrequencyOrPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithFrequencyOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
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
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testBothFrequencyAndPeriod(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(
                locale,
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX
        );
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);
        repeatComponent.setFrequency(3);

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
            return "every 2 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "chaque 2 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "alle 2 Tage";
        } else {
            return "per 2 dagen";
        }
    }

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "3 times every 2 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "3 fois chaque 2 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "3 Mal alle 2 Tage";
        } else {
            return "3 keer per 2 dagen";
        }
    }

}
