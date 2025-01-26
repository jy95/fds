package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractMaxDosePerPeriodTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Ratio;

import java.util.List;
import java.util.Locale;

public class MaxDosePerPeriodTest extends AbstractMaxDosePerPeriodTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithMaxDosePerPeriod() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(1);
        denominator.setCode("d");
        denominator.setSystem("http://hl7.org/fhir/ValueSet/units-of-time");
        ratio.setDenominator(denominator);
        dosage.setMaxDosePerPeriod(ratio);
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
