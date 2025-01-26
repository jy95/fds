package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractExtensionTest;
import org.hl7.fhir.r5.model.BooleanType;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Extension;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class ExtensionTest extends AbstractExtensionTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithExtension() {
        Dosage dosage = new Dosage();
        List<Extension> extensions = List.of(
                new Extension(
                        "http://hl7.org/fhir/StructureDefinition/timing-exact",
                        new BooleanType(true)
                )
        );
        dosage.setExtension(extensions);
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
        dosage.setExtension(extensions);
        return dosage;
    }

    @Override
    protected FDSConfigR5 generateCustomConfig(Locale locale) {
        return FDSConfigR5
                .builder()
                .displayOrder(List.of(DisplayOrder.EXTENSION))
                .locale(locale)
                .fromExtensionsToString(param -> CompletableFuture.completedFuture("(exact timing)"))
                .build();
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
