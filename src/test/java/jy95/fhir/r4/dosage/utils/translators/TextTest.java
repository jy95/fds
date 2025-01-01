package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class TextTest extends AbstractFhirTest {
    private static FhirDosageUtils dosageUtils;

    @BeforeAll
    static void setup() {
        // Initialize FhirDosageUtils with a default configuration
        dosageUtils = getDosageUtilsInstance(Locale.ENGLISH, DisplayOrder.TEXT);
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
