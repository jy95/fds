package jy95.fhir.r4;

import jy95.fhir.r4.dosage.utils.classes.FhirDosageUtils;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class AbstractFhirTest {

    public static FhirDosageUtils getDosageUtilsInstance(Locale locale, DisplayOrder displayOrder) {
        return new FhirDosageUtils(FDUConfig.builder()
                .displayOrder(List.of(displayOrder))
                .locale(locale)
                .build());
    }

    public static FhirDosageUtils getDosageUtilsInstance(FDUConfig config) {
        return new FhirDosageUtils(config);
    }

    public static Stream<Locale> localeProvider() {
        return Stream
                .of(
                        Locale.ENGLISH,
                        Locale.FRENCH,
                        Locale.forLanguageTag("nl-BE"),
                        Locale.GERMAN
                );
    }
}
