package jy95.fhir.r4.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Ratio;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RateRatioTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoRateRatio(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testEmptyRateRatio(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyNumerator(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("ml");
        ratio.setNumerator(numerator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText1(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOnlyDenominator(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity denominator = new Quantity(10);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNumeratorAndDenominator(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(1);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText3(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testTiterCase(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(1);
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(128);
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText4(locale);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testCommon(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(2);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.RATE_RATIO);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText5(locale);
        assertEquals(expected, result);
    }

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 ml";
        } else {
            return "met een verhouding van 10 ml";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 ml";
        } else {
            return "met een verhouding van 10 ml";
        }
    }

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 mg per ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 mg par ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 mg pro ml";
        } else {
            return "met een verhouding van 10 mg per ml";
        }
    }

    private String getExpectedText4(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 1 : 128";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 1 : 128";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 1 : 128";
        } else {
            return "met een verhouding van 1 : 128";
        }
    }

    private String getExpectedText5(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "at a rate of 10 mg every 2 ml";
        } else if (locale.equals(Locale.FRENCH)) {
            return "au taux de 10 mg chaque 2 ml";
        } else if (locale.equals(Locale.GERMAN)) {
            return "mit einem Verhältnis von 10 mg jeder 2 ml";
        } else {
            return "met een verhouding van 10 mg elke 2 ml";
        }
    }

}
