package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractRateRatioTest;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Quantity;
import org.hl7.fhir.r5.model.Ratio;

import java.util.List;
import java.util.Locale;

public class RateRatioTest extends AbstractRateRatioTest<FDSConfigR5, Dosage> {

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
