package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractDoseRangeTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Range;

import java.util.List;
import java.util.Locale;

public class DoseRangeTest extends AbstractDoseRangeTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateSimpleDoseRange() {
        Dosage dosage = new Dosage();
        Range range1 = new Range();
        range1.setLow(new Quantity(1));
        range1.setHigh(new Quantity(3));
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setDose(range1);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
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
