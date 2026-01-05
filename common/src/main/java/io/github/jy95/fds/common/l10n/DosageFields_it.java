package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Italian (it) resource bundle for structured dosage and field labels.
 *
 * @author jy95
 * @since 2.1.7
 */
public class DosageFields_it extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            // Linkwords
            { "linkwords.and", "e" },
            { "linkwords.then", "allora" },

            // Amount
            { "amount.range.withUnit",
                    "{condition, select, 0{{minValue} a {maxValue} {unit}} 1{fino a {maxValue} {unit}} other{almeno {minValue} {unit}}}" },
            { "amount.range.withoutUnit",
                    "{condition, select, 0{{minValue} a {maxValue}} 1{fino a {maxValue}} other{almeno {minValue}}}" },
            { "amount.ratio.denominatorLinkword", "{0, choice, 1#per|1.0<ogni}" },

            // Fields - Rate/Duration
            { "fields.rateQuantity", "ad un tasso di {0}" },
            { "fields.rateRange", "ad un tasso da {0}" },
            { "fields.rateRatio", "ad un tasso di {0}" },
            { "fields.duration", "su {0}" },
            { "fields.durationMax", "massimo {0}" },

            // Fields - Frequency
            { "fields.frequency", "{0, plural, one{{0} volta} other{{0} volte}}" },
            { "fields.frequencyAndFrequencyMax",
                    "{maxFrequency, plural, one{{frequency}-{maxFrequency} volta} other{{frequency}-{maxFrequency} volte}}" },
            { "fields.frequencyMax", "{0, plural, one{fino a {0} volta} other{fino a {0} volte}}" },

            // Fields - Period
            { "fields.period", "{period, plural, one{ogni {periodUnit}} other{ogni {period} {periodUnit}}}" },
            { "fields.periodMax", "ogni {minPeriod} a {maxPeriod} {unit}" },

            // Fields - Day/Time
            { "fields.dayOfWeek", "{dayCondition, plural, one{su {day}} other{su {day}}}" },
            { "fields.timeOfDay", "a {timeOfDay}" },
            { "fields.asNeeded", "se necessario" },
            { "fields.asNeededFor", "se necessario per {0}" },

            // Fields - Bounds
            { "fields.boundsDuration", "per {0}" },
            { "fields.boundsPeriod",
                    "{condition, select, 0{da {startDate} a {endDate}} 1{da {startDate}} other{a {endDate}}}" },
            { "fields.boundsRange", "per {0}" },

            // Fields - Count
            { "fields.count", "{0, plural, one{prendere {0} volta} other{prendere {0} volte}}" },
            { "fields.countMax",
                    "{maxCount, plural, one{prendere {minCount} a {maxCount} volta} other{prendere {minCount} a {maxCount} volte}}" },

            // Fields - Event
            { "fields.event", "{eventCondition, plural, one{su {event}} other{su {event}}}" },

            // Fields - Max Dose
            { "fields.maxDosePerLifetime", "fino a un massimo di {0} per la durata della vita del paziente" },
            { "fields.maxDosePerAdministration", "fino a un massimo di {0} per dose" },
            { "fields.maxDosePerPeriod", "fino a un massimo di {0}" },
    };

    // English version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
