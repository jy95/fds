package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Spanish (es) resource bundle for structured dosage and field labels.
 *
 * @author jy95
 * @since 2.1.6
 */
public class DosageFields_es extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            // Linkwords
            { "linkwords.and", "y" },
            { "linkwords.then", "luego" },

            // Amount
            { "amount.range.withUnit",
                    "{condition, select, 0{{minValue} a {maxValue} {unit}} 1{hasta {maxValue} {unit}} other{al menos {minValue} {unit}}}" },
            { "amount.range.withoutUnit",
                    "{condition, select, 0{{minValue} a {maxValue}} 1{hasta {maxValue}} other{al menos {minValue}}}" },
            { "amount.ratio.denominatorLinkword", "{0, choice, 1#por|1.0<cada}" },

            // Fields - Rate/Duration
            { "fields.rateQuantity", "a una tasa de {0}" },
            { "fields.rateRange", "a una tasa de {0}" },
            { "fields.rateRatio", "a una tasa de {0}" },
            { "fields.duration", "sobre {0}" },
            { "fields.durationMax", "máximo {0}" },

            // Fields - Frequency
            { "fields.frequency", "{0, plural, one{{0} vez} other{{0} veces}}" },
            { "fields.frequencyAndFrequencyMax",
                    "{maxFrequency, plural, one{{frequency}-{maxFrequency} vez} other{{frequency}-{maxFrequency} veces}}" },
            { "fields.frequencyMax", "{0, plural, one{hasta {0} vez} other{hasta {0} veces}}" },

            // Fields - Period
            { "fields.period", "{period, plural, one{cada {periodUnit}} other{cada {period} {periodUnit}}}" },
            { "fields.periodMax", "cada {minPeriod} a {maxPeriod} {unit}" },

            // Fields - Day/Time
            { "fields.dayOfWeek", "{dayCondition, plural, one{el {day}} other{los {day}}}" },
            { "fields.timeOfDay", "a las {timeOfDay}" },
            { "fields.asNeeded", "según sea necesario" },
            { "fields.asNeededFor", "según sea necesario para {0}" },

            // Fields - Bounds
            { "fields.boundsDuration", "por {0}" },
            { "fields.boundsPeriod",
                    "{condition, select, 0{desde {startDate} hasta {endDate}} 1{desde {startDate}} other{hasta {endDate}}}" },
            { "fields.boundsRange", "por {0}" },

            // Fields - Count
            { "fields.count", "{0, plural, one{tomar {0} vez} other{tomar {0} veces}}" },
            { "fields.countMax", 
                "{maxCount, plural, one{tomar de {minCount} a {maxCount} vez} other{tomar de {minCount} a {maxCount} veces}}" },

            // Fields - Event
            { "fields.event", "{eventCondition, plural, one{el {event}} other{los {event}}}" },

            // Fields - Max Dose
            { "fields.maxDosePerLifetime", "hasta un máximo de {0} para la vida del paciente" },
            { "fields.maxDosePerAdministration", "hasta un máximo de {0} por dosis" },
            { "fields.maxDosePerPeriod", "hasta un máximo de {0}" },
    };

    // Spanish version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
