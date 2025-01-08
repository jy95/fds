package jy95.fhir.r4.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Timing;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimingRepeatExtensionTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoExtension(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.TIMING_REPEAT_EXTENSION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithExtension(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        List<Extension> extensions = List.of(
                new Extension(
                        "http://hl7.org/fhir/StructureDefinition/timing-exact",
                        new BooleanType(true)
                )
        );
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setExtension(extensions);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(locale, DisplayOrder.TIMING_REPEAT_EXTENSION);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("[{\"url\":\"http://hl7.org/fhir/StructureDefinition/timing-exact\",\"value[x]\":\"true\"}]", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithExtensionCustom(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        List<Extension> extensions = List.of(
                new Extension(
                        "http://hl7.org/fhir/StructureDefinition/timing-exact",
                        new BooleanType(true)
                )
        );
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setExtension(extensions);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        FDUConfig config = FDUConfig
                .builder()
                .displayOrder(List.of(DisplayOrder.TIMING_REPEAT_EXTENSION))
                .locale(locale)
                .fromExtensionsToString(param -> CompletableFuture.completedFuture("(exact timing)"))
                .build();
        FhirDosageUtils dosageUtils = getDosageUtilsInstance(config);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("(exact timing)", result);
    }
}
