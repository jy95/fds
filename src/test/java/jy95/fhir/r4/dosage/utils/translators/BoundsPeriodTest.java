package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundsPeriodTest {
    private static FhirDosageUtils getDosageUtilsInstance(Locale locale) {
        return new FhirDosageUtils(FDUConfig.builder()
                .displayOrder(List.of(DisplayOrder.BOUNDS_PERIOD))
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
    void testNoBoundsPeriod(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale);
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
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale);
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
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText2(locale);
        assertEquals(expected, result);
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
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale);
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
    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "to Feb 7, 2015, 1:28:17 PM";
        } else if (locale.equals(Locale.FRENCH)) {
            return "si nécessaire pour head pain";
        } else if (locale.equals(Locale.GERMAN)) {
            return "bis 07.02.2015, 13:28:17";
        } else {
            return "tot 7 feb 2015, 13:28:17";
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
