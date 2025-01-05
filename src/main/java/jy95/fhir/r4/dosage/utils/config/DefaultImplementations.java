package jy95.fhir.r4.dosage.utils.config;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.Objects;
import java.util.List;
import java.util.stream.Collectors;

import jy95.fhir.r4.dosage.utils.miscellaneous.MultiResourceBundleControl;
import jy95.fhir.r4.dosage.utils.types.DoseAndRateKey;
import org.hl7.fhir.r4.model.*;

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

    public static Type selectDosageAndRateField(
            List<Dosage.DosageDoseAndRateComponent> doseAndRates,
            DoseAndRateKey extractor
    ) {
        // Keep it simple, use the first entry as most of the time, it is enough for everyone
        return extractor.extract(doseAndRates.getFirst());
    }

    public static ResourceBundle selectResourceBundle(Locale locale) {
        var bundleControl = new MultiResourceBundleControl(
                "translations",
                "common",
                "daysOfWeek",
                "eventTiming",
                "quantityComparator",
                "unitsOfTime"
        );
        return ResourceBundle.getBundle(
                bundleControl.getBaseName(),
                locale,
                bundleControl
        );
    }
}
