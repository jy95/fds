package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import org.hl7.fhir.r5.model.Dosage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class PatientInstructionTest extends AbstractFhirTest {
    private static DosageAPIR5 dosageUtils;

    @BeforeAll
    static void setup() {
        // Initialize DosageAPIR5 with a default configuration
        dosageUtils = getDosageAPI(Locale.ENGLISH, DisplayOrder.PATIENT_INSTRUCTION);
    }

    @Test
    void testNoPatientInstruction() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @Test
    void testWithPatientInstruction() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        dosage.setPatientInstruction("SIG");
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("SIG", result);
    }
}
