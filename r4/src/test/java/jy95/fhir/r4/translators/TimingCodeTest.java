package jy95.fhir.r4.translators;

import jy95.fhir.r4.DosageAPIR4;
import jy95.fhir.r4.AbstractFhirTest;
import jy95.fhir.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimingCodeTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoCode(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeText(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("Take medication in the morning on weekends and days off work");
        Timing timing = new Timing();
        timing.setCode(cc1);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Take medication in the morning on weekends and days off work", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/timing-abbreviation", "BID", "Two times a day at institution specified time")
                )
        );
        Timing timing = new Timing();
        timing.setCode(cc1);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Two times a day at institution specified time", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithTimingCodeCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/timing-abbreviation", "BID", null)
                )
        );
        Timing timing = new Timing();
        timing.setCode(cc1);
        dosage.setTiming(timing);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.TIMING_CODE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("BID", result);
    }

}
