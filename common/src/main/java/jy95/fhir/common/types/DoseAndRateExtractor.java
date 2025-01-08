package jy95.fhir.common.types;

@FunctionalInterface
public interface DoseAndRateExtractor<A, B> {
    B extract(A doseAndRateComponent);
}