package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractTimeOfDayTest;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;

import java.util.List;
import java.util.Locale;

public class TimeOfDayTest extends AbstractTimeOfDayTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateSingleTimeOfDay() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.getRepeat().addTimeOfDay("15:00:00");
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateMultipleTimeOfDay() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.getRepeat().addTimeOfDay("15:00:00");
        timing.getRepeat().addTimeOfDay("12:12:12");
        dosage.setTiming(timing);
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
