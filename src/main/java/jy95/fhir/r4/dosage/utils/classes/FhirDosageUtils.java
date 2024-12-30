package jy95.fhir.r4.dosage.utils.classes;

import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.miscellaneous.Translators;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Getter
public class FhirDosageUtils {
    private final FDUConfig config;
    private final Translators translators;

    public FhirDosageUtils() {
        this(FDUConfig.builder().build());
    }

    public FhirDosageUtils(FDUConfig config){
        this.config = config;
        this.translators = new Translators(config);
    }

    public CompletableFuture<String> getFields(Dosage dosage, DisplayOrder... fields){

        var separator = this.config.getDisplaySeparator();
        // Create a list of CompletableFutures for each field translation
        var translatorsFields = Arrays
                .stream(fields)
                .map(translators::getTranslator)
                .filter(translator -> translator.isPresent(dosage))
                .map(translator -> translator.convert(dosage))
                .toList();

        // Combine the results of all CompletableFutures into a single result
        return CompletableFuture
                .allOf(translatorsFields.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> translatorsFields
                        .stream()
                        .map(s -> s.getNow(""))
                        .collect(Collectors.joining(separator))
                );
    }

}
