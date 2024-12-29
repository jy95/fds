package jy95.fhir.r4.dosage.utils.config;

import java.util.concurrent.CompletableFuture;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.model.CodeableConcept;
import org.hl7.fhir.r4.model.Quantity;
import org.hl7.fhir.r4.model.Extension;

public class DefaultImplementations {

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

            if (codeableConcept.getCoding().isEmpty()) {
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
                    .map(Object::toString)
                    .collect(Collectors.joining(" "));
        });
    }
}
