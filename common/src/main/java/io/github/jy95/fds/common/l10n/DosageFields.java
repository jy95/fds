package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * English (en) resource bundle for structured dosage and field labels.
 */
public class DosageFields extends ListResourceBundle {
    
    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        // Linkwords
        {"linkwords.and", "and"},
        {"linkwords.then", "then"},

        // Amount
        {"amount.range.withUnit", "{condition, select, 0{{minValue} to {maxValue} {unit}} 1{up to {maxValue} {unit}} other{at least {minValue} {unit}}}"},
        {"amount.range.withoutUnit", "{condition, select, 0{{minValue} to {maxValue}} 1{up to {maxValue}} other{at least {minValue}}}"},
        {"amount.ratio.denominatorLinkword", "{0, choice, 1#per|1.0<every}"},

        // Fields - Rate/Duration
        {"fields.rateQuantity", "at a rate of {0}"},
        {"fields.rateRange", "at a rate of {0}"},
        {"fields.rateRatio", "at a rate of {0}"},
        {"fields.duration", "over {0}"},
        {"fields.durationMax", "maximum {0}"},

        // Fields - Frequency
        {"fields.frequency", "{0, plural, one{{0} time} other{{0} times}}"},
        {"fields.frequencyAndFrequencyMax", "{maxFrequency, plural, one{{frequency}-{maxFrequency} time} other{{frequency}-{maxFrequency} times}}"},
        {"fields.frequencyMax", "{0, plural, one{up to {0} time} other{up to {0} times}}"},

        // Fields - Period
        {"fields.period", "{period, plural, one{every {periodUnit}} other{every {period} {periodUnit}}}"},
        {"fields.periodMax", "every {minPeriod} to {maxPeriod} {unit}"},

        // Fields - Day/Time
        {"fields.dayOfWeek", "{dayCondition, plural, one{on {day}} other{on {day}}}"},
        {"fields.timeOfDay", "at {timeOfDay}"},
        {"fields.asNeeded", "as required"},
        {"fields.asNeededFor", "as required for {0}"},

        // Fields - Bounds
        {"fields.boundsDuration", "for {0}"},
        {"fields.boundsPeriod", "{condition, select, 0{from {startDate} to {endDate}} 1{from {startDate}} other{to {endDate}} }"},
        {"fields.boundsRange", "for {0}"},

        // Fields - Count
        {"fields.count", "{0, plural, one{take {0} time} other{take {0} times}}"},
        {"fields.countMax", "{maxCount, plural, one{take {minCount} to {maxCount} time} other{take {minCount} to {maxCount} times}}"},

        // Fields - Event
        {"fields.event", "{eventCondition, plural, one{on {event}} other{on {event}}}"},

        // Fields - Max Dose
        {"fields.maxDosePerLifetime", "up to a maximum of {0} for the lifetime of the patient"},
        {"fields.maxDosePerAdministration", "up to a maximum of {0} per dose"},
        {"fields.maxDosePerPeriod", "up to a maximum of {0}"},
    };

    // English version
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
