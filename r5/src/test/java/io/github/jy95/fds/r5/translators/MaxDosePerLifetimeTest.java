package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractMaxDosePerLifetimeTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;

import java.util.List;
import java.util.Locale;

public class MaxDosePerLifetimeTest extends AbstractMaxDosePerLifetimeTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithMaxDosePerLifetime() {
        Dosage dosage = new Dosage();
        Quantity quantity = new Quantity();
        quantity.setValue(50);
        quantity.setUnit("mg");
        dosage.setMaxDosePerLifetime(quantity);
        return dosage;
    }

    @Override
    public DosageAPI<FDSConfigR5, Dosage> getDosageAPI(Locale locale, DisplayOrder displayOrder) {
        return new DosageAPIR5(FDSConfigR5.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    @Override
    public DosageAPI<FDSConfigR5, Dosage> getDosageAPI(FDSConfigR5 config) {
        return new DosageAPIR5(config);
    }

    @Override
    public Dosage generateEmptyDosage() {
        return new Dosage();
    }
}
