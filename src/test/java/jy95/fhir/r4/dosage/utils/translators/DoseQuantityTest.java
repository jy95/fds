package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.AbstractFhirTest;
import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DoseQuantityTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoDoseQuantity(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_QUANTITY);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testSimpleDoseQuantity(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_QUANTITY);
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("5 ml", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityWithComparator(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_QUANTITY);
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        quantity1.setComparator(Quantity.QuantityComparator.LESS_THAN);
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("< 5 ml", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityWithoutUnit(Locale locale) throws ExecutionException, InterruptedException {
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(locale, DisplayOrder.DOSE_QUANTITY);
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("5", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testDoseQuantityCustom(Locale locale) throws ExecutionException, InterruptedException {
        FDUConfig config = FDUConfig
                .builder()
                .selectDosageAndRateField(
                        (doseAndRateComponentList, doseAndRateKey)
                                -> doseAndRateKey.extract(doseAndRateComponentList.get(1)))
                .build();
        FhirDosageUtils dosageUtils = DayOfWeekTest.getDosageUtilsInstance(config);
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        Quantity quantity2 = new Quantity(8);
        quantity2.setUnit("ml");
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        doseAndRateComponent1.setType(
                new CodeableConcept(
                        new Coding(
                                "http://terminology.hl7.org/ValueSet/dose-rate-type",
                                "calculated",
                                "Calculated"
                        )
                )
        );
        DosageDoseAndRateComponent doseAndRateComponent2 = new DosageDoseAndRateComponent();
        doseAndRateComponent2.setDose(quantity2);
        doseAndRateComponent2.setType(
                new CodeableConcept(
                        new Coding(
                                "http://terminology.hl7.org/ValueSet/dose-rate-type",
                                "ordered",
                                "Ordered"
                        )
                )
        );
        dosage.setDoseAndRate(List.of(doseAndRateComponent1, doseAndRateComponent2));

        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("8 ml", result);
    }

}
