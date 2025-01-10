package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractTimeOfDay;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.PrimitiveType;

import java.util.List;

public class TimeOfDayR4 extends AbstractTimeOfDay<FDSConfigR4, Dosage> {

    public TimeOfDayR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected List<String> getTimes(Dosage dosage) {
        return dosage
                .getTiming()
                .getRepeat()
                .getTimeOfDay()
                .stream()
                .map(PrimitiveType::getValue)
                .toList();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && timing.getRepeat().hasTimeOfDay();
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }
}
