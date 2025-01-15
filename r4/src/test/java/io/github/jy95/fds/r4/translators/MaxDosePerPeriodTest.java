package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractMaxDosePerPeriodTest;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Ratio;

import java.util.List;
import java.util.Locale;

public class MaxDosePerPeriodTest extends AbstractMaxDosePerPeriodTest<FDSConfigR4, Dosage> {

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
