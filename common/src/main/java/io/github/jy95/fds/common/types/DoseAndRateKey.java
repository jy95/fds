package io.github.jy95.fds.common.types;

/**
 * Enum representing the different keys for dose and rate components.
 * These keys are used to identify the various attributes in a dose and rate structure.
 *
 * @author jy95
 */
public enum DoseAndRateKey {
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.doseQuantity}.
     * Represents a specific dose quantity.
     */
    DOSE_QUANTITY,
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.doseRange}.
     * Represents a range of doses.
     */
    DOSE_RANGE,
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateQuantity}.
     * Represents a specific rate quantity.
     */
    RATE_QUANTITY,
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateRange}.
     * Represents a range of rates.
     */
    RATE_RANGE,
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateRatio}.
     * Represents a ratio defining the rate.
     */
    RATE_RATIO
}
