import java.util.ListResourceBundle;

/**
 * German (de) resource bundle for structured dosage and field labels.
 * Uses native UTF-8 characters to avoid encoding issues.
 */
public class DosageFields_de extends ListResourceBundle {
    
    // German version
    @Override
    public Object[][] getContents() {
        return contents;
    }

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        // Linkwords
        {"linkwords.and", "und"},
        {"linkwords.then", "dann"},

        // Amount
        {"amount.range.withUnit", "{condition, select, 0{zwischen {minValue} und {maxValue} {unit}} 1{bis {maxValue} {unit}} other{mindestens {minValue} {unit}}}"},
        {"amount.range.withoutUnit", "{condition, select, 0{zwischen {minValue} und {maxValue}} 1{bis {maxValue}} other{mindestens {minValue}}}"},
        {"amount.ratio.denominatorLinkword", "{0, choice, 1#pro|1.0<jeder}"},

        // Fields - Rate/Duration
        {"fields.rateQuantity", "mit einem Verhältnis von {0}"},
        {"fields.rateRange", "mit einem Verhältnis von {0}"},
        {"fields.rateRatio", "mit einem Verhältnis von {0}"},
        {"fields.duration", "für {0}"},
        {"fields.durationMax", "maximal {0}"},

        // Fields - Frequency
        {"fields.frequency", "{0, plural, one{{0} Mal} other{{0} Mal}}"},
        {"fields.frequencyAndFrequencyMax", "{maxFrequency, plural, one{{frequency}-{maxFrequency} Mal} other{{frequency}-{maxFrequency} Mal}}"},
        {"fields.frequencyMax", "{0, plural, one{bis zu {0} Mal} other{bis zu {0} Mal}}"},

        // Fields - Period
        {"fields.period", "{period, plural, one{jede {periodUnit}} other{alle {period} {periodUnit}}}"},
        {"fields.periodMax", "jede {minPeriod} bis zu {maxPeriod} {unit}"},

        // Fields - Day/Time
        {"fields.dayOfWeek", "{dayCondition, plural, one{am {day}} other{am {day}}}"},
        {"fields.timeOfDay", "um {timeOfDay}"},
        {"fields.asNeeded", "bei Bedarf"},
        {"fields.asNeededFor", "bei Bedarf für {0}"},

        // Fields - Bounds
        {"fields.boundsDuration", "für {0}"},
        {"fields.boundsPeriod", "{condition, select, 0{von {startDate} bis {endDate}} 1{ab {startDate}} other{bis {endDate}} }"},
        {"fields.boundsRange", "für {0}"},

        // Fields - Count
        {"fields.count", "{0, plural, one{{0} Mal nehmen} other{{0} Mal nehmen}}"},
        {"fields.countMax", "{maxCount, plural, one{von {minCount} bis {maxCount} Mal nehmen} other{von {minCount} bis {maxCount} Mal nehmen}}"},

        // Fields - Event
        {"fields.event", "{eventCondition, plural, one{am {event}} other{am {event}}}"},

        // Fields - Max Dose
        {"fields.maxDosePerLifetime", "bis zu einer maximalen Menge von {0} über die Lebenszeit des Patienten"},
        {"fields.maxDosePerAdministration", "bis zu einer maximalen Menge von {0} pro Dosis"},
        {"fields.maxDosePerPeriod", "bis zu einer maximalen Menge von {0}"},
    };

}
