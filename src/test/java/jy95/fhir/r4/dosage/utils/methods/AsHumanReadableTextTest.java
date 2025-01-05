package jy95.fhir.r4.dosage.utils.methods;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import org.hl7.fhir.r4.model.Dosage;
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
        FhirDosageUtils dosageUtils = new FhirDosageUtils();
        List<Dosage> dosageList = List.of();
        String result = dosageUtils.asHumanReadableText(dosageList).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testOneItem(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = new FhirDosageUtils();
        Dosage dosage1 = new Dosage();
        dosage1.setPatientInstruction("A prendre avec de l'eau");
        List<Dosage> dosageList = List.of(dosage1);
        String result = dosageUtils.asHumanReadableText(dosageList).get();
        assertEquals("A prendre avec de l'eau", result);
    }

}
