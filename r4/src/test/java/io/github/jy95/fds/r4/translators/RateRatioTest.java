package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractRateRatioTest;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Ratio;

import java.util.List;
import java.util.Locale;

public class RateRatioTest extends AbstractRateRatioTest<FDSConfigR4, Dosage> {
    
    @Override
    protected Dosage generateEmptyRateRatio() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateOnlyNumerator() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("ml");
        ratio.setNumerator(numerator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateOnlyDenominator() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity denominator = new Quantity(10);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateNumeratorAndDenominator() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(1);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateTiterCase() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(1);
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(128);
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
        dosage.setDoseAndRate(List.of(doseAndRateComponent1));
        return dosage;
    }

    @Override
    protected Dosage generateCommonCase() {
        Dosage dosage = new Dosage();
        Ratio ratio = new Ratio();
        Quantity numerator = new Quantity(10);
        numerator.setUnit("mg");
        ratio.setNumerator(numerator);
        Quantity denominator = new Quantity(2);
        denominator.setUnit("ml");
        ratio.setDenominator(denominator);
        Dosage.DosageDoseAndRateComponent doseAndRateComponent1 = new Dosage.DosageDoseAndRateComponent();
        doseAndRateComponent1.setRate(ratio);
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
