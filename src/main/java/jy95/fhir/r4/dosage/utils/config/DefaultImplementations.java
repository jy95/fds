package jy95.fhir.r4.dosage.utils.config;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.Objects;

import org.hl7.fhir.r4.model.Quantity;

public class DefaultImplementations {

    public static CompletableFuture<String> defaultFromFHIRQuantityUnitToString(Quantity quantity) {
        return CompletableFuture.supplyAsync(() -> {

            if (quantity.getCode() != null) {
                return quantity.getCode();
            }

            if (Objects.nonNull(quantity.getUnit())) {
                return quantity.getUnit();
            }

            return "";
        });
    }
}
