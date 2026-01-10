package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Portuguese (pt) resource bundle for structured dosage and field labels.
 *
 * @author jy95
 * @since 2.1.8
 */
public class DosageFields_pt extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            // Linkwords
            { "linkwords.and", "e" },
            { "linkwords.then", "então" },

            // Amount
            { "amount.range.withUnit",
                    "{condition, select, 0{{minValue} para {maxValue} {unit}} 1{até {maxValue} {unit}} other{pelo menos {minValue} {unit}}}" },
            { "amount.range.withoutUnit",
                    "{condition, select, 0{{minValue} para {maxValue}} 1{até {maxValue}} other{pelo menos {minValue}}}" },
            { "amount.ratio.denominatorLinkword", "{0, choice, 1#por|1.0<cada}" },

            // Fields - Rate/Duration
            { "fields.rateQuantity", "a uma taxa de {0}" },
            { "fields.rateRange", "a uma taxa de {0}" },
            { "fields.rateRatio", "a uma taxa de {0}" },
            { "fields.duration", "durante {0}" },
            { "fields.durationMax", "máximo {0}" },

            // Fields - Frequency
            { "fields.frequency", "{0, plural, one{{0} vez} other{{0} vezes}}" },
            { "fields.frequencyAndFrequencyMax",
                    "{maxFrequency, plural, one{{frequency} a {maxFrequency} vez} other{{frequency} a {maxFrequency} vezes}}" },
            { "fields.frequencyMax", "{0, plural, one{até {0} vez} other{até {0} vezes}}" },

            // Fields - Period
            { "fields.period", "{period, plural, one{cada {periodUnit}} other{cada {period} {periodUnit}}}" },
            { "fields.periodMax", "cada {minPeriod} para {maxPeriod} {unit}" },

            // Fields - Day/Time
            { "fields.dayOfWeek", "{dayCondition, plural, one{na {day}} other{na {day}}}" },
            { "fields.timeOfDay", "às {timeOfDay}" },
            { "fields.asNeeded", "como necessário" },
            { "fields.asNeededFor", "como necessário para {0}" },

            // Fields - Bounds
            { "fields.boundsDuration", "por {0}" },
            { "fields.boundsPeriod",
                    "{condition, select, 0{de {startDate} a {endDate}} 1{de {startDate}} other{a {endDate}}}" },
            { "fields.boundsRange", "por {0}" },

            // Fields - Count
            { "fields.count", "{0, plural, one{tomar {0} vez} other{tomar {0} vezes}}" },
            { "fields.countMax",
                    "{maxCount, plural, one{tomar {minCount} a {maxCount} vez} other{tomar {minCount} a {maxCount} vezes}}" },

            // Fields - Event
            { "fields.event", "{eventCondition, plural, one{em {event}} other{em {event}}}" },

            // Fields - Max Dose
            { "fields.maxDosePerLifetime", "até um máximo de {0} durante o tempo de vida do doente" },
            { "fields.maxDosePerAdministration", "até um máximo de {0} por dose" },
            { "fields.maxDosePerPeriod", "até um máximo de {0}" },
    };

    // English version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
