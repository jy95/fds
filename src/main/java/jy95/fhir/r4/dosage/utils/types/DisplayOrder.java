package jy95.fhir.r4.dosage.utils.types;

//import lombok.Getter;
//import lombok.RequiredArgsConstructor;

/**
 * Represents the available display orders
 */
public enum DisplayOrder {
    /**
     * Display "method"
     */
    METHOD("method"),
    /**
     * Display "doseAndRate.doseQuantity"
     */
    DOSE_QUANTITY("doseQuantity"),
    /**
     * Display "doseAndRate.doseRange"
     */
    DOSE_RANGE("doseRange"),
    /**
     * Display "doseAndRate.rateRatio"
     */
    RATE_RATIO("rateRatio"),
    /**
     * Display "doseAndRate.rateQuantity"
     */
    RATE_QUANTITY("rateQuantity"),
    /**
     * Display "doseAndRate.rateRange"
     */
    RATE_RANGE("rateRange"),
    /**
     * Display "timing.repeat.duration" / "timing.repeat.durationMax"
     */
    DURATION_DURATION_MAX("durationDurationMax"),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax"
     */
    FREQUENCY_FREQUENCY_MAX("frequencyFrequencyMax"),
    /**
     * Display "timing.repeat.period" / "timing.repeat.periodMax"
     */
    PERIOD_PERIOD_MAX("periodPeriodMax"),
    /**
     * Display "timing.repeat.frequency" / "timing.repeat.frequencyMax" / "timing.repeat.period" / "timing.repeat.periodMax"
     */
    FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX("frequencyFrequencyMaxPeriodPeriodMax"),
    /**
     * Display "timing.repeat.offset" / "timing.repeat.when"
     */
    OFFSET_WHEN("offsetWhen"),
    /**
     * Display "timing.repeat.dayOfWeek"
     */
    DAY_OF_WEEK("dayOfWeek"),
    /**
     * Display "timing.repeat.timeOfDay"
     */
    TIME_OF_DAY("timeOfDay"),
    /**
     * Display "route"
     */
    ROUTE("route"),
    /**
     * Display "site"
     */
    SITE("site"),
    /**
     * Display "asNeededBoolean" / "asNeededCodeableConcept" / "asNeeded" / "asNeededFor"
     */
    AS_NEEDED("asNeeded"),
    /**
     * Display "timing.repeat.boundsDuration"
     */
    BOUNDS_DURATION("boundsDuration"),
    /**
     * Display "timing.repeat.boundsPeriod"
     */
    BOUNDS_PERIOD("boundsPeriod"),
    /**
     * Display "timing.repeat.boundsRange"
     */
    BOUNDS_RANGE("boundsRange"),
    /**
     * Display "timing.repeat.count" / "timing.repeat.countMax"
     */
    COUNT_COUNT_MAX("countCountMax"),
    /**
     * Display "timing.event"
     */
    TIMING_EVENT("event"),
    /**
     * Display "timing.code"
     */
    TIMING_CODE("code"),
    /**
     * Display "maxDosePerPeriod"
     */
    MAX_DOSE_PER_PERIOD("maxDosePerPeriod"),
    /**
     * Display "maxDosePerAdministration"
     */
    MAX_DOSE_PER_ADMINISTRATION("maxDosePerAdministration"),
    /**
     * Display "maxDosePerLifetime"
     */
    MAX_DOSE_PER_LIFETIME("maxDosePerLifetime"),
    /**
     * Display "additionalInstruction"
     */
    ADDITIONAL_INSTRUCTION("additionalInstruction"),
    /**
     * Display "patientInstruction"
     */
    PATIENT_INSTRUCTION("patientInstruction"),
    /**
     * Display "text"
     */
    TEXT("text"),
    /**
     * Display "extension"
     */
    EXTENSION("extension"),
    /**
     * Display "timing.extension"
     */
    TIMING_EXTENSION("timingExtension"),
    /**
     * Display "timing.repeat.extension"
     */
    TIMING_REPEAT_EXTENSION("timingRepeatExtension"),
    /**
     * Display "modifierExtension"
     */
    MODIFIER_EXTENSION("modifierExtension"),
    /**
     * Display "timing.modifierExtension"
     */
    TIMING_MODIFIER_EXTENSION("timingModifierExtension");

    private final String item;

    DisplayOrder(String item) {
        this.item = item;
    }
}