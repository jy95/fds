package jy95.fhir.r4.dosage.utils.classes;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;

import lombok.Getter;

@Getter
public class FhirDosageUtils {
    private final FDUConfig config;

    FhirDosageUtils() {
        this.config = FDUConfig.builder().build();
    }

    FhirDosageUtils(FDUConfig config){
        this.config = config;
    }

}
