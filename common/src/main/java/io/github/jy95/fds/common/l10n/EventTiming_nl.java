package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Dutch (nl) resource bundle for event timing codes.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event
 *      Timing</a>
 * @author jy95
 * @since 2.0.1
 */
public class EventTiming_nl extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            { "MORN", "tijdens de ochtend" },
            { "MORN.early", "tijdens de vroege ochtend" },
            { "MORN.late", "tijdens de late ochtend" },
            { "NOON", "rond het middaguur" },
            { "AFT", "tijdens de namiddag" },
            { "AFT.early", "tijdens de vroege namiddag" },
            { "AFT.late", "tijdens de late namiddag" },
            { "EVE", "tijdens de avond" },
            { "EVE.early", "tijdens de vroege avond" },
            { "EVE.late", "tijdens de late avond" },
            { "NIGHT", "tijdens de nacht" },

            { "PHS", "na het slapen" },
            { "IMD", "een enkele keer" },
            { "HS", "voor het slapen" },
            { "WAKE", "bij het ontwaken" },

            // Meal-related timings
            { "C", "tijdens een maaltijd" },
            { "CM", "bij het ontbijt" },
            { "CD", "bij de lunch" },
            { "CV", "bij het diner" },

            { "AC", "voor de maaltijd" },
            { "ACM", "voor het ontbijt" },
            { "ACD", "voor de lunch" },
            { "ACV", "voor het diner" },

            { "PC", "na de maaltijd" },
            { "PCM", "na het ontbijt" },
            { "PCD", "na de lunch" },
            { "PCV", "na het diner" },
    };

    // Dutch version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }

}
