package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractTimingEventTest;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;

import java.util.List;
import java.util.Locale;

public class TimingEventTest extends AbstractTimingEventTest<FDSConfigR4, Dosage> {

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
