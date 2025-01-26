package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractTimingEventTest;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Timing;

import java.util.List;
import java.util.Locale;

public class TimingEventTest extends AbstractTimingEventTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateSingle() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.setEvent(
                List.of(
                        new DateTimeType("2024-01-01")
                )
        );
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateMultiple() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        timing.setEvent(
                // As explained on https://build.fhir.org/datatypes.html#dateTime
                List.of(
                        new DateTimeType("2018"),
                        new DateTimeType("1973-06"),
                        new DateTimeType("1905-08-23")
                )
        );
        dosage.setTiming(timing);
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
