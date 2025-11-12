package io.github.jy95.fds.r4.config;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.config.DefaultImplementations;

import io.github.jy95.fds.common.types.FDSOperations;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Dosage.DosageDoseAndRateComponent;

/**
 * R4 Configuration class for controlling how dosage is handled and displayed.
 *
 * @author jy95
 */
@Getter
@SuperBuilder
public class FDSConfigR4 extends FDSConfig implements FDSOperations<
        Quantity,
        CodeableConcept,
        Extension,
        Dosage,
        DosageDoseAndRateComponent,
        Type>  {

    // Default implementations, in case not provided by user
    @Builder.Default private final Function<Quantity, CompletableFuture<String>> fromFHIRQuantityUnitToString = DefaultImplementationsR4::fromFHIRQuantityUnitToString;
    @Builder.Default private final Function<CodeableConcept, CompletableFuture<String>> fromCodeableConceptToString = DefaultImplementationsR4::fromCodeableConceptToString;
    @Builder.Default private final Function<List<Extension>, CompletableFuture<String>> fromExtensionsToString = DefaultImplementations::fromExtensionsToString;
    @Builder.Default private final BiFunction<List<DosageDoseAndRateComponent>, DoseAndRateKey, Type> selectDosageAndRateField = (doseAndRateComponentList, doseAndRateKey) ->
        DefaultImplementations.selectDosageAndRateField(
            doseAndRateComponentList,
            doseAndRateKey,
            DefaultImplementationsR4.EXTRACTOR_MAP
        );
    @Builder.Default private final BiFunction<Dosage, Predicate<Dosage.DosageDoseAndRateComponent>, Boolean> hasMatchingComponent = DefaultImplementationsR4::hasMatchingComponent;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> fromFHIRQuantityUnitToString(Quantity quantity) {
        return this.fromFHIRQuantityUnitToString.apply(quantity);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> fromCodeableConceptToString(CodeableConcept codeableConcept) {
        return this.fromCodeableConceptToString.apply(codeableConcept);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> fromExtensionsToString(List<Extension> extensions) {
        return this.fromExtensionsToString.apply(extensions);
    }

    /** {@inheritDoc} */
    @Override
    public Type selectDosageAndRateField(List<DosageDoseAndRateComponent> doseAndRateComponentList, DoseAndRateKey doseAndRateKey) {
        return this.selectDosageAndRateField.apply(doseAndRateComponentList, doseAndRateKey);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasMatchingComponent(Dosage dosage, Predicate<DosageDoseAndRateComponent> predicate) {
        return this.hasMatchingComponent.apply(dosage, predicate);
    }
}
