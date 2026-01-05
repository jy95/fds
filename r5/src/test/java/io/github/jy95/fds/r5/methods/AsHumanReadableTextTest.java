package io.github.jy95.fds.r5.methods;

import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.AbstractFhirTest;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AsHumanReadableTextTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testEmptyList(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR5 dosageUtils = new DosageAPIR5();
        List<Dosage> dosageList = List.of();
        String result = dosageUtils.asHumanReadableText(dosageList).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOneItem(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR5 dosageUtils = getDosageAPI(
                FDSConfigR5
                        .builder()
                        .locale(locale)
                        .build()
        );
        Dosage dosage1 = new Dosage();
        dosage1.setPatientInstruction("A prendre avec de l'eau");
        List<Dosage> dosageList = List.of(dosage1);
        String result = dosageUtils.asHumanReadableText(dosageList).get();
        assertEquals("A prendre avec de l'eau", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testMultipleItems(Locale locale) throws ExecutionException, InterruptedException {
        DosageAPIR5 dosageUtils = getDosageAPI(
                FDSConfigR5
                        .builder()
                        .locale(locale)
                        .build()
        );
        Dosage dosage1 = getFirstDosage();
        Dosage dosage2 = getSecondDosage();
        Dosage dosage3 = getThirdDosage();
        List<Dosage> dosageList = List.of(dosage1, dosage2, dosage3);
        String result = dosageUtils.asHumanReadableText(dosageList).get();
        String expected = getExpectedText(locale);
        assertEquals(expected, result);
    }

    // For the setup of testMultipleItems
    private static Dosage getFirstDosage() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        dosage.setSequence(1);
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setCount(2);
        Quantity duration = new Duration();
        duration.setValue(2);
        duration.setCode("wk");
        duration.setSystem("http://hl7.org/fhir/ValueSet/duration-units");
        repeatComponent.setBounds(duration);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    // For the setup of testMultipleItems
    private static Dosage getSecondDosage() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        dosage.setSequence(2);
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setCount(1);
        repeatComponent.setPeriod(1);
        repeatComponent.setPeriodUnit(Timing.UnitsOfTime.D);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    // For the setup of testMultipleItems
    private static Dosage getThirdDosage() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        dosage.setSequence(2);
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setCount(1);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        dosage.setAsNeeded(true);
        return dosage;
    }

    private static String getExpectedText(Locale locale) {
        if (locale.equals(Locale.ENGLISH)) {
            return "for 2 weeks - take 2 times then every day - take 1 time and as required - take 1 time";
        } else if (locale.equals(Locale.FRENCH)) {
            return "pour 2 semaines - prendre 2 fois puis chaque jour - prendre 1 fois et si nécessaire - prendre 1 fois";
        } else if (locale.equals(Locale.GERMAN)) {
            return "für 2 Wochen - 2 Mal nehmen dann jede Tag - 1 Mal nehmen und bei Bedarf - 1 Mal nehmen";
        } else if (locale.equals(Locale.forLanguageTag("es"))) {
            return "por 2 semanas - tomar 2 veces luego cada día - tomar 1 vez y según sea necesario - tomar 1 vez";
        } else if (locale.equals(Locale.ITALIAN)) { 
            return "per 2 settimane - impiegare 2 volte ogni giorno - impiegare 1 volta e come richiesto - impiegare 1 volta";
        } else {
            return "gedurende 2 weken - 2 keer nemen vervolgens elke dag - 1 keer nemen en indien nodig - 1 keer nemen";
        }
    }

}
