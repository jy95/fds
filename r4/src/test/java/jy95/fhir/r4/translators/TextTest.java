package jy95.fhir.r4.translators;

import jy95.fhir.r4.DosageAPIR4;
import jy95.fhir.r4.AbstractFhirTest;
import jy95.fhir.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class TextTest extends AbstractFhirTest {
    private static DosageAPIR4 dosageUtils;

    @BeforeAll
    static void setup() {
        // Initialize DosageAPIR4 with a default configuration
        dosageUtils = getDosageAPI(Locale.ENGLISH, DisplayOrder.TEXT);
    }

    @Test
    void testNoText() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @Test
    void testWithText() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        dosage.setText("SIG");
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("SIG", result);
    }
}
