package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * French (fr) resource bundle for structured dosage and field labels.
 * Uses native UTF-8 characters to avoid encoding issues.
 *
 * @author jy95
 * @since 2.0.1
 */
public class DosageFields_fr extends ListResourceBundle {
    
    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        // Linkwords
        {"linkwords.and", "et"},
        {"linkwords.then", "puis"},

        // Amount
        {"amount.range.withUnit", "{condition, select, 0{{minValue} à {maxValue} {unit}} 1{{maxValue} {unit} maximum} other{au moins {minValue} {unit}}}"},
        {"amount.range.withoutUnit", "{condition, select, 0{{minValue} à {maxValue}} 1{{maxValue} maximum} other{au moins {minValue}}}"},
        {"amount.ratio.denominatorLinkword", "{0, choice, 1#par|1.0<chaque}"},

        // Fields - Rate/Duration
        {"fields.rateQuantity", "au taux de {0}"},
        {"fields.rateRange", "au taux de {0}"},
        {"fields.rateRatio", "au taux de {0}"},
        {"fields.duration", "durant {0}"},
        {"fields.durationMax", "maximum {0}"},

        // Fields - Frequency
        {"fields.frequency", "{0, plural, one{{0} fois} other{{0} fois}}"},
        {"fields.frequencyAndFrequencyMax", "{maxFrequency, plural, one{{frequency}-{maxFrequency} fois} other{{frequency}-{maxFrequency} fois}}"},
        {"fields.frequencyMax", "{0, plural, one{jusqu'à {0} fois} other{jusqu'à {0} fois}}"},

        // Fields - Period
        {"fields.period", "{period, plural, one{chaque {periodUnit}} other{chaque {period} {periodUnit}}}"},
        {"fields.periodMax", "chaque {minPeriod} à {maxPeriod} {unit}"},

        // Fields - Day/Time
        {"fields.dayOfWeek", "{dayCondition, plural, one{le {day}} other{les {day}}}"},
        {"fields.timeOfDay", "à {timeOfDay}"},
        {"fields.asNeeded", "si nécessaire"},
        {"fields.asNeededFor", "si nécessaire pour {0}"},

        // Fields - Bounds
        {"fields.boundsDuration", "pour {0}"},
        {"fields.boundsPeriod", "{condition, select, 0{du {startDate} au {endDate}} 1{à partir du {startDate}} other{jusqu’au {endDate}} }"},
        {"fields.boundsRange", "pour {0}"},

        // Fields - Count
        {"fields.count", "{0, plural, one{prendre {0} fois} other{{0} fois}}"},
        {"fields.countMax", "{maxCount, plural, one{prendre {minCount} à {maxCount} fois} other{{minCount} à {maxCount} fois}}"},

        // Fields - Event
        {"fields.event", "{eventCondition, plural, one{le {event}} other{les {event}}}"},

        // Fields - Max Dose
        {"fields.maxDosePerLifetime", "jusqu’à un maximum de {0} pour la durée de vie du patient"},
        {"fields.maxDosePerAdministration", "jusqu’à un maximum de {0} par dose"},
        {"fields.maxDosePerPeriod", "jusqu’à un maximum de {0}"},
    };

    // French version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
