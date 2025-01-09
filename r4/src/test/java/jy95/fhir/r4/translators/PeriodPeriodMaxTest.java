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

public class PeriodPeriodMaxTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithPeriodOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBothPeriod(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.PERIOD_PERIOD_MAX);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setPeriod(2);
        repeatComponent.setPeriodMax(3);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    private String getExpectedText1(Locale locale) {
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

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "every 2 to 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "chaque 2 à 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "jede 2 bis zu 3 Tage";
        } else {
            return "elke 2 tot 3 dagen";
        }
    }

}
