package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.DosageAPI;
import io.github.jy95.fds.r5.DosageAPIR5;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.translators.AbstractRouteTest;
import org.hl7.fhir.r5.model.CodeableConcept;
import org.hl7.fhir.r5.model.Coding;
import org.hl7.fhir.r5.model.Dosage;

import java.util.List;
import java.util.Locale;

public class RouteTest extends AbstractRouteTest<FDSConfigR5, Dosage> {

    @Override
    protected Dosage generateWithRouteText() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("With or after food");
        dosage.setRoute(cc1);
        return dosage;
    }

    @Override
    protected Dosage generateWithRouteCodeAndDisplay() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/route-codes", "26643006", "Oral route")
                )
        );
        dosage.setRoute(cc1);
        return dosage;
    }

    @Override
    protected Dosage generateWithRouteCodeOnly() {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/route-codes", "26643006", null)
                )
        );
        dosage.setRoute(cc1);
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
