package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;

public class DayOfWeekTest {
    private static FhirDosageUtils dosageUtils;

    @BeforeAll
    static void setup() {
        // Initialize FhirDosageUtils with a default configuration
        dosageUtils = new FhirDosageUtils(FDUConfig.builder()
                .displayOrder(List.of(DisplayOrder.DAY_OF_WEEK))
                .build());
    }

    @Test
    void testNoDayOfWeek() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @Test
    void testSingleDayOfWeek() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.FRI);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("on Friday", result);
    }

    @Test
    void testMultipleDayOfWeek() throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.MON);
        repeatComponent.addDayOfWeek(Timing.DayOfWeek.FRI);
        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("on Monday and Friday", result);
    }
}
