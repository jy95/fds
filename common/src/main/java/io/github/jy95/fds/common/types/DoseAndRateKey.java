package io.github.jy95.fds.common.types;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum representing the different keys for dose and rate components.
 * These keys are used to identify the various attributes in a dose and rate
 * structure.
 *
 * @author jy95
 * @since 1.0.0
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum DoseAndRateKey {
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.doseQuantity}.
     * Represents a specific dose quantity.
     */
    DOSE_QUANTITY(null),
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.doseRange}.
     * Represents a range of doses.
     */
    DOSE_RANGE(null),
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateQuantity}.
     * Represents a specific rate quantity.
     */
    RATE_QUANTITY("fields.rateQuantity"),
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateRange}.
     * Represents a range of rates.
     */
    RATE_RANGE("fields.rateRange"),
    /**
     * Refers to the FHIR dosage field {@code doseAndRate.rateRatio}.
     * Represents a ratio defining the rate.
     */
    RATE_RATIO("fields.rateRatio");

    private final String messageKey;
}
