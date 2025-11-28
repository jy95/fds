package io.github.jy95.fds.common.types;

import lombok.Getter;

import java.util.List;

/**
 * Represents the available display orders
 *
 * @author jy95
 * @since 1.0.0
 */
@Getter
public enum DisplayOrder {
    /**
     * Display "method"
     */
    METHOD(
        SpecComponent.DOSAGE,
        List.of("method")
    ),
    /**
     * Display "doseAndRate.doseQuantity"
     */
    DOSE_QUANTITY(
        SpecComponent.DOSAGE,
        List.of("doseAndRate.doseQuantity")
    ),
    /**
     * Display "doseAndRate.doseRange"
     */
    DOSE_RANGE(
        SpecComponent.DOSAGE,
        List.of("doseAndRate.doseRange")
    ),
    /**
     * Display "doseAndRate.rateRatio"
     */
    RATE_RATIO(
        SpecComponent.DOSAGE,
        List.of("doseAndRate.rateRatio")
    ),
    /**
     * Display "doseAndRate.rateQuantity"
     */
    RATE_QUANTITY(
        SpecComponent.DOSAGE,
        List.of("doseAndRate.rateQuantity")
    ),
    /**
     * Display "doseAndRate.rateRange"
     */
    RATE_RANGE(
        SpecComponent.DOSAGE,
        List.of("doseAndRate.rateRange")
    ),
    /**
     * Display "timing.repeat.duration" / "timing.repeat.durationMax"
     */
    DURATION_DURATION_MAX(
        SpecComponent.TIMING_REPEAT,
        List.of("duration", "durationMax")
    ),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    FREQUENCY_FREQUENCY_MAX(
        SpecComponent.TIMING_REPEAT,
        List.of("frequency", "frequencyMax")
    ),
    /**
     * Display "timing.repeat.period" / "timing.repeat.periodMax"
     */
    PERIOD_PERIOD_MAX(
        SpecComponent.TIMING_REPEAT,
        List.of("period", "periodMax")
    ),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax" /
     * "timing.repeat.period" / "timing.repeat.periodMax"
     */
    FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX(
        SpecComponent.TIMING_REPEAT,
        List.of("frequency", "frequencyMax", "period", "periodMax")
    ),
    /**
     * Display "timing.repeat.offset" / "timing.repeat.when"
     */
    OFFSET_WHEN(
        SpecComponent.TIMING_REPEAT,
        List.of("offset", "when")
    ),
    /**
     * Display "timing.repeat.dayOfWeek"
     */
    DAY_OF_WEEK(
        SpecComponent.TIMING_REPEAT,
        List.of("dayOfWeek")
    ),
    /**
     * Display "timing.repeat.timeOfDay"
     */
    TIME_OF_DAY(
        SpecComponent.TIMING_REPEAT,
        List.of("timeOfDay")
    ),
    /**
     * Display "route"
     */
    ROUTE(
        SpecComponent.DOSAGE,
        List.of("route")
    ),
    /**
     * Display "site"
     */
    SITE(
        SpecComponent.DOSAGE,
        List.of("site")
    ),
    /**
     * Display "asNeededBoolean" / "asNeededCodeableConcept" / "asNeeded" /
     * "asNeededFor"
     */
    AS_NEEDED(
        SpecComponent.DOSAGE,
        List.of("asNeededBoolean", "asNeededCodeableConcept", "asNeeded", "asNeededFor")
    ),
    /**
     * Display "timing.repeat.boundsDuration"
     */
    BOUNDS_DURATION(
        SpecComponent.TIMING_REPEAT,
        List.of("boundsDuration")
    ),
    /**
     * Display "timing.repeat.boundsPeriod"
     */
    BOUNDS_PERIOD(
        SpecComponent.TIMING_REPEAT,
        List.of("boundsPeriod")
    ),
    /**
     * Display "timing.repeat.boundsRange"
     */
    BOUNDS_RANGE(
        SpecComponent.TIMING_REPEAT,
        List.of("boundsRange")
    ),
    /**
     * Display "timing.repeat.count" / "timing.repeat.countMax"
     */
    COUNT_COUNT_MAX(
        SpecComponent.TIMING_REPEAT,
        List.of("count", "countMax")
    ),
    /**
     * Display "timing.event"
     */
    TIMING_EVENT(
        SpecComponent.TIMING,
        List.of("event")
    ),
    /**
     * Display "timing.code"
     */
    TIMING_CODE(
        SpecComponent.TIMING,
        List.of("code")
    ),
    /**
     * Display "maxDosePerPeriod"
     */
    MAX_DOSE_PER_PERIOD(
        SpecComponent.DOSAGE,
        List.of("maxDosePerPeriod")
    ),
    /**
     * Display "maxDosePerAdministration"
     */
    MAX_DOSE_PER_ADMINISTRATION(
        SpecComponent.DOSAGE,
        List.of("maxDosePerAdministration")
    ),
    /**
     * Display "maxDosePerLifetime"
     */
    MAX_DOSE_PER_LIFETIME(
        SpecComponent.DOSAGE,
        List.of("maxDosePerLifetime")
    ),
    /**
     * Display "additionalInstruction"
     */
    ADDITIONAL_INSTRUCTION(
        SpecComponent.DOSAGE,
        List.of("additionalInstruction")
    ),
    /**
     * Display "patientInstruction"
     */
    PATIENT_INSTRUCTION(
        SpecComponent.DOSAGE,
        List.of("patientInstruction")
    ),
    /**
     * Display "text"
     */
    TEXT(
        SpecComponent.DOSAGE,
        List.of("text")
    ),
    /**
     * Display "extension"
     */
    EXTENSION(
        SpecComponent.DOSAGE,
        List.of("extension")
    ),
    /**
     * Display "timing.extension"
     */
    TIMING_EXTENSION(
        SpecComponent.TIMING,
        List.of("extension")
    ),
    /**
     * Display "timing.repeat.extension"
     */
    TIMING_REPEAT_EXTENSION(
        SpecComponent.TIMING_REPEAT,
        List.of("extension")
    ),
    /**
     * Display "modifierExtension"
     */
    MODIFIER_EXTENSION(
        SpecComponent.DOSAGE,
        List.of("modifierExtension")
    ),
    /**
     * Display "timing.modifierExtension"
     */
    TIMING_MODIFIER_EXTENSION(
        SpecComponent.TIMING,
        List.of("modifierExtension")
    );

    /**
     * Getter for the <code>field</code>(s) covered by the display order
     */
    private final List<String> fields;

    /**
     * Getter for the class covered by the display order
     */
    private final SpecComponent component;

    DisplayOrder(SpecComponent component, List<String> fields) {
        this.component = component;
        this.fields = fields;
    }

}
