package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Italian (it) resource bundle for event timing codes.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event
 *      Timing</a>
 * @author jy95
 * @since 
 */
public class EventTiming_it extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            { "MORN", "durante la mattina" },
            { "MORN.early", "durante la mattina presto" },
            { "MORN.late", "durante la tarda mattinata" },
            { "NOON", "intorno alle 12:00" },
            { "AFT", "durante il pomeriggio" },
            { "AFT.early", "durante il primo pomeriggio" },
            { "AFT.late", "durante il tardo pomeriggio" },
            { "EVE", "durante la serata" },
            { "EVE.early", "durante la prima sera" },
            { "EVE.late", "durante la tarda sera" },
            { "NIGHT", "durante la notte" },

            { "PHS", "dopo il sonno" },
            { "IMD", "una volta sola" },
            { "HS", "prima di dormire" },
            { "WAKE", "a vegliare" },

            // Meal-related timings
            { "C", "a pranzo" },
            { "CM", "a colazione" },
            { "CD", "a pranzo" },
            { "CV", "a cena" },

            { "AC", "prima del pasto" },
            { "ACM", "prima colazione" },
            { "ACD", "prima di pranzo" },
            { "ACV", "prima di cena" },

            { "PC", "dopo pasto" },
            { "PCM", "dopo colazione" },
            { "PCD", "dopo pranzo" },
            { "PCV", "dopo cena" },
    };

    // English version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
