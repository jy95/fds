package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.r4.DosageAPIR4;
import io.github.jy95.fds.r4.AbstractFhirTest;
import io.github.jy95.fds.common.types.DisplayOrder;
import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Coding;
import org.hl7.fhir.r4.model.Dosage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SiteTest extends AbstractFhirTest {

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testNoSite(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteText(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setText("With or after food");
        dosage.setSite(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("With or after food", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteCodeAndDisplay(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/approach-site-codes", "1910005", "Entire ear")
                )
        );
        dosage.setSite(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("Entire ear", result);
    }

    @ParameterizedTest
    @MethodSource("localeProvider")
    void testWithSiteCodeOnly(Locale locale) throws ExecutionException, InterruptedException {
        Dosage dosage = new Dosage();
        CodeableConcept cc1 = new CodeableConcept();
        cc1.setCoding(
                List.of(
                        new Coding("http://hl7.org/fhir/ValueSet/approach-site-codes", "1910005", null)
                )
        );
        dosage.setSite(cc1);
        DosageAPIR4 dosageUtils = getDosageAPI(locale, DisplayOrder.SITE);
        String result = dosageUtils.asHumanReadableText(dosage).get();
        assertEquals("1910005", result);
    }
}
