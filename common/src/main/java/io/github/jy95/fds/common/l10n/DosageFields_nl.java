package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * Dutch (nl) resource bundle for structured dosage and field labels.
 * Uses native UTF-8 characters to avoid encoding issues.
 *
 * @author jy95
 * @since 2.0.1
 */
public class DosageFields_nl extends ListResourceBundle {
    
    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        // Linkwords
        {"linkwords.and", "en"},
        {"linkwords.then", "vervolgens"},

        // Amount
        {"amount.range.withUnit", "{condition, select, 0{tussen {minValue} en {maxValue} {unit}} 1{tot {maxValue} {unit}} other{minstens {minValue} {unit}}}"},
        {"amount.range.withoutUnit", "{condition, select, 0{tussen {minValue} en {maxValue}} 1{tot {maxValue}} other{minstens {minValue}}}"},
        {"amount.ratio.denominatorLinkword", "{0, choice, 1#per|1.0<elke}"},

        // Fields - Rate/Duration
        {"fields.rateQuantity", "met een verhouding van {0}"},
        {"fields.rateRange", "met een verhouding van {0}"},
        {"fields.rateRatio", "met een verhouding van {0}"},
        {"fields.duration", "gedurende {0}"},
        {"fields.durationMax", "maximaal {0}"},

        // Fields - Frequency
        {"fields.frequency", "{0, plural, one{{0} keer} other{{0} keer}}"},
        {"fields.frequencyAndFrequencyMax", "{maxFrequency, plural, one{{frequency}-{maxFrequency} keer} other{{frequency}-{maxFrequency} keer}}"},
        {"fields.frequencyMax", "{0, plural, one{tot {0} keer} other{tot {0} keer}}"},

        // Fields - Period
        {"fields.period", "{period, plural, one{elke {periodUnit}} other{per {period} {periodUnit}}}"},
        {"fields.periodMax", "elke {minPeriod} tot {maxPeriod} {unit}"},

        // Fields - Day/Time
        {"fields.dayOfWeek", "{dayCondition, plural, one{op {day}} other{op {day}}}"},
        {"fields.timeOfDay", "om {timeOfDay}"},
        {"fields.asNeeded", "indien nodig"},
        {"fields.asNeededFor", "zoals nodig voor {0}"},

        // Fields - Bounds
        {"fields.boundsDuration", "gedurende {0}"},
        {"fields.boundsPeriod", "{condition, select, 0{van {startDate} tot {endDate}} 1{van {startDate}} other{tot {endDate}} }"},
        {"fields.boundsRange", "gedurende {0}"},

        // Fields - Count
        {"fields.count", "{0, plural, one{{0} keer nemen} other{{0} keer nemen}}"},
        {"fields.countMax", "{maxCount, plural, one{{minCount} tot {maxCount} keer nemen} other{{minCount} tot {maxCount} keer nemen}}"},

        // Fields - Event
        {"fields.event", "{eventCondition, plural, one{op {event}} other{op {event}}}"},

        // Fields - Max Dose
        {"fields.maxDosePerLifetime", "tot een maximum van {0} gedurende de levensduur van de patiënt"},
        {"fields.maxDosePerAdministration", "tot een maximum van {0} per dosis"},
        {"fields.maxDosePerPeriod", "tot een maximum van {0}"},
    };

    // Dutch version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
