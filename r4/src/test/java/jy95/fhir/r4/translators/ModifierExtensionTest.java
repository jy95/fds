package jy95.fhir.r4.translators;

import jy95.fhir.r4.DosageAPIR4;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.AbstractFhirTest;
import jy95.fhir.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Extension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModifierExtensionTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoExtension(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.MODIFIER_EXTENSION);
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
        dosage.setModifierExtension(extensions);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.MODIFIER_EXTENSION);
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
        dosage.setModifierExtension(extensions);
        FDSConfigR4 config = FDSConfigR4
                .builder()
                .displayOrder(List.of(DisplayOrder.MODIFIER_EXTENSION))
                .locale(locale)
                .fromExtensionsToString(param -> CompletableFuture.completedFuture("(exact timing)"))
                .build();
        DosageAPIR4 dosageUtils = getDosageAPI(config);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("(exact timing)", result);
    }
}
