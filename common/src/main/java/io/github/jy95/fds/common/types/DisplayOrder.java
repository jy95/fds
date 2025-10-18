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
    METHOD(List.of("method")),
    /**
     * Display "doseAndRate.doseQuantity"
     */
    DOSE_QUANTITY(List.of("doseAndRate.doseQuantity")),
    /**
     * Display "doseAndRate.doseRange"
     */
    DOSE_RANGE(List.of("doseAndRate.doseRange")),
    /**
     * Display "doseAndRate.rateRatio"
     */
    RATE_RATIO(List.of("doseAndRate.rateRatio")),
    /**
     * Display "doseAndRate.rateQuantity"
     */
    RATE_QUANTITY(List.of("doseAndRate.rateQuantity")),
    /**
     * Display "doseAndRate.rateRange"
     */
    RATE_RANGE(List.of("doseAndRate.rateRange")),
    /**
     * Display "timing.repeat.duration" / "timing.repeat.durationMax"
     */
    DURATION_DURATION_MAX(List.of("timing.repeat.duration", "timing.repeat.durationMax")),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    FREQUENCY_FREQUENCY_MAX(List.of("timing.repeat.frequency", "timing.repeat.frequencyMax")),
    /**
     * Display "timing.repeat.period" / "timing.repeat.periodMax"
     */
    PERIOD_PERIOD_MAX(List.of("timing.repeat.period", "timing.repeat.periodMax")),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax"
     */
    FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX(List.of("timing.repeat.frequency", "timing.repeat.frequencyMax", "timing.repeat.period", "timing.repeat.periodMax")),
    /**
     * Display "timing.repeat.offset" / "timing.repeat.when"
     */
    OFFSET_WHEN(List.of("timing.repeat.offset", "timing.repeat.when")),
    /**
     * Display "timing.repeat.dayOfWeek"
     */
    DAY_OF_WEEK(List.of("timing.repeat.dayOfWeek")),
    /**
     * Display "timing.repeat.timeOfDay"
     */
    TIME_OF_DAY(List.of("timing.repeat.timeOfDay")),
    /**
     * Display "route"
     */
    ROUTE(List.of("route")),
    /**
     * Display "site"
     */
    SITE(List.of("site")),
    /**
     * Display "asNeededBoolean" / "asNeededCodeableConcept" / "asNeeded" / "asNeededFor"
     */
    AS_NEEDED(List.of("asNeededBoolean", "asNeededCodeableConcept", "asNeeded", "asNeededFor")),
    /**
     * Display "timing.repeat.boundsDuration"
     */
    BOUNDS_DURATION(List.of("timing.repeat.boundsDuration")),
    /**
     * Display "timing.repeat.boundsPeriod"
     */
    BOUNDS_PERIOD(List.of("timing.repeat.boundsPeriod")),
    /**
     * Display "timing.repeat.boundsRange"
     */
    BOUNDS_RANGE(List.of("timing.repeat.boundsRange")),
    /**
     * Display "timing.repeat.count" / "timing.repeat.countMax"
     */
    COUNT_COUNT_MAX(List.of("timing.repeat.count", "timing.repeat.countMax")),
    /**
     * Display "timing.event"
     */
    TIMING_EVENT(List.of("timing.event")),
    /**
     * Display "timing.code"
     */
    TIMING_CODE(List.of("timing.code")),
    /**
     * Display "maxDosePerPeriod"
     */
    MAX_DOSE_PER_PERIOD(List.of("maxDosePerPeriod")),
    /**
     * Display "maxDosePerAdministration"
     */
    MAX_DOSE_PER_ADMINISTRATION(List.of("maxDosePerAdministration")),
    /**
     * Display "maxDosePerLifetime"
     */
    MAX_DOSE_PER_LIFETIME(List.of("maxDosePerLifetime")),
    /**
     * Display "additionalInstruction"
     */
    ADDITIONAL_INSTRUCTION(List.of("additionalInstruction")),
    /**
     * Display "patientInstruction"
     */
    PATIENT_INSTRUCTION(List.of("patientInstruction")),
    /**
     * Display "text"
     */
    TEXT(List.of("text")),
    /**
     * Display "extension"
     */
    EXTENSION(List.of("extension")),
    /**
     * Display "timing.extension"
     */
    TIMING_EXTENSION(List.of("timing.extension")),
    /**
     * Display "timing.repeat.extension"
     */
    TIMING_REPEAT_EXTENSION(List.of("timing.repeat.extension")),
    /**
     * Display "modifierExtension"
     */
    MODIFIER_EXTENSION(List.of("modifierExtension")),
    /**
     * Display "timing.modifierExtension"
     */
    TIMING_MODIFIER_EXTENSION(List.of("timing.modifierExtension"));

    /**
     *  Getter for the <code>field</code>(s) covered by the display order
     */
    private final List<String> fields;

    DisplayOrder(List<String> fields) {
        this.fields = fields;
    }

}
