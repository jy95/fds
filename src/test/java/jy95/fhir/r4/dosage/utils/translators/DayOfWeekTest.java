package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DayOfWeekTest {

    // Factory to create instance
    private static FhirDosageUtils getDosageUtilsInstance(Locale locale) {
        return new FhirDosageUtils(FDUConfig.builder()
                .displayOrder(List.of(DisplayOrder.DAY_OF_WEEK))
                .locale(locale)
                .build());
    }

    // Locale I want to cover
    private static Stream<Locale> localeProvider() {
        return Stream
                .of(
                        Locale.ENGLISH,
                        Locale.FRENCH,
                        Locale.of("nl"),
                        Locale.GERMAN
                );
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSingleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.FRI);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedSingleDayText(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleDayOfWeek(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.MON);
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.FRI);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedMultipleDaysText(locale);
        assertEquals(expectedResult, result);
    }

    // For the parametrized test of single form
    private static String getExpectedSingleDayText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Friday";
        } else if (locale.equals(Locale.FRENCH)) {
            return "le vendredi";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am Freitag";
        } else {
            return "op vrijdag";
        }
    }

    // For the parametrized test of multiple form
    private String getExpectedMultipleDaysText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "on Monday and Friday";
        } else if (locale.equals(Locale.FRENCH)) {
            return "les lundi et vendredi";
        } else if (locale.equals(Locale.GERMAN)) {
            return "am Montag und Freitag";
        } else {
            return "op maandag en vrijdag";
        }
    }
}
