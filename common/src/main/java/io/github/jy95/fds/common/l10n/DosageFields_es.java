package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Spanish (es) resource bundle for structured dosage and field labels.
 *
 * @author jy95
 * @since 2.0.1
 */
public class DosageFields_es extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            // Linkwords
            { "linkwords.and", "y" },
            { "linkwords.then", "entonces" },

            // Amount
            { "amount.range.withUnit",
                    "{condition, select, 0{{minValue} a {maxValue} {unit}} 1{hasta {maxValue} {unit}} other{al menos {minValue} {unit}}}" },
            { "amount.range.withoutUnit",
                    "{condition, select, 0{{minValue} a {maxValue}} 1{hasta {maxValue}} other{al menos {minValue}}}" },
            { "amount.ratio.denominatorLinkword", "{0, choice, }" },

            // Fields - Rate/Duration
            { "fields.rateQuantity", "a una tasa {0}" },
            { "fields.rateRange", "a una tasa {0}" },
            { "fields.rateRatio", "a una tasa {0}" },
            { "fields.duration", "sobre {0}" },
            { "fields.durationMax", "máximo {0}" },

            // Fields - Frequency
            { "fields.frequency", "{0, plural, one{{0} tiempo} other{{0} veces}}" },
            { "fields.frequencyAndFrequencyMax",
                    "{maxFrequency, plural, one{{frequency}-{maxFrequency} tiempo} other{{frequency}-{maxFrequency} veces}}" },
            { "fields.frequencyMax", "{0, plural, one{hasta {0} tiempo} other{hasta {0} veces}}" },

            // Fields - Period
            { "fields.period", "{period, plural, one{cada {periodUnit}} other{cada {period} {periodUnit}}}" },
            { "fields.periodMax", "cada {minPeriod} a {maxPeriod} {unit}" },

            // Fields - Day/Time
            { "fields.dayOfWeek", "{dayCondition, plural, one{on {day}} other{on {day}}}" },
            { "fields.timeOfDay", "a {timeOfDay}" },
            { "fields.asNeeded", "según sea necesario" },
            { "fields.asNeededFor", "según sea necesario {0}" },

            // Fields - Bounds
            { "fields.boundsDuration", "para {0}" },
            { "fields.boundsPeriod",
                    "{condition, select, 0{desde {startDate} a {endDate}} 1{desde {startDate}} other{a {endDate}}}" },
            { "fields.boundsRange", "para {0}" },

            // Fields - Count
            { "fields.count", "{0, plural, one{Toma. {0} tiempo} other{Toma. {0} veces}}" },
            { "fields.countMax",
                    "{maxCount, plural, one{Toma. {minCount} a {maxCount} tiempo} other{Toma. {minCount} a {maxCount} veces}}" },

            // Fields - Event
            { "fields.event", "{eventCondition, plural, one{on {event}} other{on {event}}}" },

            // Fields - Max Dose
            { "fields.maxDosePerLifetime", "hasta un máximo {0} para la vida del paciente" },
            { "fields.maxDosePerAdministration", "hasta un máximo {0} por dosis" },
            { "fields.maxDosePerPeriod", "hasta un máximo {0}" },
    };

    // Spanish version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
