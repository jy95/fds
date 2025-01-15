package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.translators.AbstractTimingRepeatExtensionTest;
import org.hl7.fhir.r4.model.BooleanType;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Timing;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class TimingRepeatExtensionTest extends AbstractTimingRepeatExtensionTest<FDSConfigR4, Dosage> {

    @Override
    protected Dosage generateWithExtension() {
        Dosage dosage = new Dosage();
        List<Extension> extensions = List.of(
                new Extension(
                        "http://hl7.org/fhir/StructureDefinition/timing-exact",
                        new BooleanType(true)
                )
        );
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setExtension(extensions);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected Dosage generateWithExtensionCustom() {
        Dosage dosage = new Dosage();
        List<Extension> extensions = List.of(
                new Extension(
                        "http://hl7.org/fhir/StructureDefinition/timing-exact",
                        new BooleanType(true)
                )
        );
        Timing timing = new Timing();
        Timing.TimingRepeatComponent timingRepeatComponent = new Timing.TimingRepeatComponent();
        timingRepeatComponent.setExtension(extensions);
        timing.setRepeat(timingRepeatComponent);
        dosage.setTiming(timing);
        return dosage;
    }

    @Override
    protected FDSConfigR4 generateCustomConfig(Locale locale) {
        return FDSConfigR4
                .builder()
                .displayOrder(List.of(DisplayOrder.TIMING_REPEAT_EXTENSION))
                .locale(locale)
                .fromExtensionsToString(param -> CompletableFuture.completedFuture("(exact timing)"))
                .build();
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
