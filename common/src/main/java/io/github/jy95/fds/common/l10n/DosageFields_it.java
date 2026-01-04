package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Italian (it) resource bundle for structured dosage and field labels.
 *
 * @author jy95
 * @since 
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
            { "amount.ratio.denominatorLinkword", "{0, choice, }" },

            // Fields - Rate/Duration
            { "fields.rateQuantity", "ad un tasso {0}" },
            { "fields.rateRange", "ad un tasso {0}" },
            { "fields.rateRatio", "ad un tasso {0}" },
            { "fields.duration", "su {0}" },
            { "fields.durationMax", "massimo {0}" },

            // Fields - Frequency
            { "fields.frequency", "{0, plural, one{{0} tempo} other{{0} volte}}" },
            { "fields.frequencyAndFrequencyMax",
                    "{maxFrequency, plural, one{{frequency}- No.{maxFrequency} tempo} other{{frequency}- No.{maxFrequency} volte}}" },
            { "fields.frequencyMax", "{0, plural, one{fino a {0} tempo} other{fino a {0} volte}}" },

            // Fields - Period
            { "fields.period", "{period, plural, one{ogni {periodUnit}} other{ogni {period} {periodUnit}}}" },
            { "fields.periodMax", "ogni {minPeriod} a {maxPeriod} {unit}" },

            // Fields - Day/Time
            { "fields.dayOfWeek", "{dayCondition, plural, one{su {day}} other{su {day}}}" },
            { "fields.timeOfDay", "a {timeOfDay}" },
            { "fields.asNeeded", "come richiesto" },
            { "fields.asNeededFor", "come richiesto per {0}" },

            // Fields - Bounds
            { "fields.boundsDuration", "per {0}" },
            { "fields.boundsPeriod",
                    "{condition, select, 0{da {startDate} a {endDate}} 1{da {startDate}} other{a {endDate}}}" },
            { "fields.boundsRange", "per {0}" },

            // Fields - Count
            { "fields.count", "{0, plural, one{prendere {0} tempo} other{prendere {0} volte}}" },
            { "fields.countMax",
                    "{maxCount, plural, one{prendere {minCount} a {maxCount} tempo} other{prendere {minCount} a {maxCount} volte}}" },

            // Fields - Event
            { "fields.event", "{eventCondition, plural, one{su {event}} other{su {event}}}" },

            // Fields - Max Dose
            { "fields.maxDosePerLifetime", "fino a un massimo di {0} per la vita del paziente" },
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
