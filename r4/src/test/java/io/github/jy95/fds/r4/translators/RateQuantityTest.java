package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractRateQuantityTest;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;

import java.util.List;
import java.util.Locale;

public class RateQuantityTest extends AbstractRateQuantityTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateSimpleRateQuantity() {
        Dosage dosage = new Dosage();
        Quantity quantity1 = new Quantity(5);
        quantity1.setUnit("ml");
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(quantity1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
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