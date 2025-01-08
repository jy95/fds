package jy95.fhir.r4.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Duration;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoundsDurationTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoBoundsDuration(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_DURATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithBoundsDuration(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Quantity duration = new Duration();
        duration.setValue(3);
        duration.setCode("d");
        duration.setSystem("http://hl7.org/fhir/ValueSet/duration-units");
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setBounds(duration);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.BOUNDS_DURATION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    // For the parametrized test of first test
    private static String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 3 days";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 3 jours";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für 3 Tage";
        } else {
            return "gedurende 3 dagen";
        }
    }
}
