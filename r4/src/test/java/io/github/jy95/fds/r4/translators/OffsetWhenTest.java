package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractOffsetWhenTest;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Timing;

import java.util.List;
import java.util.Locale;

public class OffsetWhenTest extends AbstractOffsetWhenTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateWithWhenOnly() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addWhen(Timing.EventTiming.MORN);
        repeatComponent.addWhen(Timing.EventTiming.NIGHT);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithWhenAndCount() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.addWhen(Timing.EventTiming.MORN);
        repeatComponent.addWhen(Timing.EventTiming.NIGHT);
        repeatComponent.setOffset(90);

        timing.setRepeat(repeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithCountOnly() {
        Dosage dosage = new Dosage();
        Timing timing = new Timing();
        Timing.TimingRepeatComponent repeatComponent = new Timing.TimingRepeatComponent();
        repeatComponent.setOffset(30);

        timing.setRepeat(repeatComponent);
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
