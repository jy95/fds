package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OffsetWhenTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoOffsetAndWhen(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addWhen(Timing.EventTiming.MORN);
        repeatComponent.addWhen(Timing.EventTiming.NIGHT);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText1(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithWhenAndCount(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addWhen(Timing.EventTiming.MORN);
        repeatComponent.addWhen(Timing.EventTiming.NIGHT);
        repeatComponent.setOffset(90);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText2(locale);
        assertEquals(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithCountOnly(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR4 dosageUtils = DayOfWeekTest.getDosageAPI(locale, DisplayOrder.OFFSET_WHEN);
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setOffset(30);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expectedResult = getExpectedText3(locale);
        assertEquals(expectedResult, result);
    }

    private String getExpectedText1(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "during the morning and during the night";
        } else if (locale.equals(Locale.FRENCH)) {
            return "durant le matin et durant la nuit";
        } else if (locale.equals(Locale.GERMAN)) {
            return "während des Vormittags und über Nacht";
        } else {
            return "tijdens de ochtend en tijdens de nacht";
        }
    }

    private String getExpectedText2(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "1 hour and 30 minutes during the morning and during the night";
        } else if (locale.equals(Locale.FRENCH)) {
            return "1 heure et 30 minutes durant le matin et durant la nuit";
        } else if (locale.equals(Locale.GERMAN)) {
            return "1 Stunde und 30 Minuten während des Vormittags und über Nacht";
        } else {
            return "1 uur en 30 minuten tijdens de ochtend en tijdens de nacht";
        }
    }

    private String getExpectedText3(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "30 minutes";
        } else if (locale.equals(Locale.FRENCH)) {
            return "30 minutes";
        } else if (locale.equals(Locale.GERMAN)) {
            return "30 Minuten";
        } else {
            return "30 minuten";
        }
    }

}
