package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractTimeOfDay;
import jy95.fhir.r4.config.FDSConfigR4;
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
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasTimeOfDay();
    }
}
