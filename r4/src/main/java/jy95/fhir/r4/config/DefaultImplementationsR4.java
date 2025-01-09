package jy95.fhir.r4.config;

import java.util.concurrent.CompletableFuture;
import java.util.Objects;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jy95.fhir.common.types.DoseAndRateKey;
import jy95.fhir.r4.functions.DoseAndRateRegistryR4;
import org.hl7.fhir.r4.model.*;

public final class DefaultImplementationsR4 {
    
    public static CompletableFuture<String> fromFHIRQuantityUnitToString(Quantity quantity) {
        return CompletableFuture.supplyAsync(() -> {

            if (Objects.isNull(quantity)) {
                return null;
            }

            if (Objects.nonNull(quantity.getCode())) {
                return quantity.getCode();
            }

            if (Objects.nonNull(quantity.getUnit())) {
                return quantity.getUnit();
            }

            return "";
        });
    }

    public static CompletableFuture<String> fromCodeableConceptToString(CodeableConcept codeableConcept) {
        return CompletableFuture.supplyAsync(() -> {

            if (Objects.isNull(codeableConcept)) {
                return null;
            }

            if (Objects.nonNull(codeableConcept.getText())) {
                return codeableConcept.getText();
            }

            if (!codeableConcept.hasCoding()) {
                return null;
            }

            var firstCode = codeableConcept.getCodingFirstRep();
            var display = firstCode.getDisplay();
            var code = firstCode.getCode();

            return (Objects.nonNull(display)) ? display : code;
        });
    }

    public static CompletableFuture<String> fromExtensionsToString(List<Extension> extensions) {
        return CompletableFuture.supplyAsync(() -> {

            if (extensions.isEmpty()) {
                return null;
            }

            return extensions
                    .stream()
                    .map(ext -> {

                        StringBuilder sb = new StringBuilder();
                        sb.append("{");

                        if (ext.hasUrl()) {
                            sb.append("\"url\":\"").append(ext.getUrl()).append("\"");
                        }
                        if (ext.hasUrl() && ext.hasValue()) {
                            sb.append(",");
                        }
                        if (ext.hasValue()) {
                            sb.append("\"value[x]\":\"").append(
                                    ext.getValueAsPrimitive().getValueAsString()
                            ).append("\"");
                        }

                        sb.append("}");
                        return sb.toString();
                    })
                    .collect(Collectors.joining(", ", "[", "]"));
        });
    }

    public static Type selectDosageAndRateField(List<Dosage.DosageDoseAndRateComponent> doseAndRateComponentList, DoseAndRateKey doseAndRateKey) {
        var extractor = DoseAndRateRegistryR4.getInstance().getExtractor(doseAndRateKey);
        var firstRep = doseAndRateComponentList.getFirst();
        return extractor.extract(firstRep);
    }

    public static boolean hasMatchingComponent(Dosage dosage, Predicate<Dosage.DosageDoseAndRateComponent> predicate) {
        return dosage.hasDoseAndRate() && dosage
                .getDoseAndRate()
                .stream()
                .anyMatch(predicate);
    }
}
