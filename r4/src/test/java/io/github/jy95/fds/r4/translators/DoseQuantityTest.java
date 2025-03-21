package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractDoseQuantityTest;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;
import org.hl7.fhir.r4.model.Quantity;

import java.util.List;
import java.util.Locale;

public class DoseQuantityTest extends AbstractDoseQuantityTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateSimpleDoseQuantity() {
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateDoseQuantityWithComparator() {
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        quantity1.setComparator(Quantity.QuantityComparator.LESS_THAN);
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateDoseQuantityWithoutUnit() {
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        DosageDoseAndRateComponent doseAndRateComponent1 = new DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateDoseQuantityCustom() {
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
        dosage.addDoseAndRate(doseAndRateComponent1);
        dosage.addDoseAndRate(doseAndRateComponent2);
        return dosage;
    }

    @Override
    protected FDSConfigR4 generateCustomConfig() {
        return FDSConfigR4
                .builder()
                .displayOrder(List.of(DisplayOrder.DOSE_QUANTITY))
                .selectDosageAndRateField(
                        (doseAndRateComponentList, doseAndRateKey)
                                -> doseAndRateComponentList.get(1).getDoseQuantity())
                .build();
    }

    @Override
    public DosageAPI<FDSConfigR4, Dosage> getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR4(FDSConfigR4.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    @Override
    public DosageAPI<FDSConfigR4, Dosage> getDosageAPI(FDSConfigR4 config) {
        return new DosageAPIR4(config);
    }

    @Override
    public Dosage generateEmptyDosage() {
        return new Dosage();
    }
}
