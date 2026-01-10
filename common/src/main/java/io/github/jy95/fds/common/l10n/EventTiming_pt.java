package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Portuguese (pt) resource bundle for event timing codes.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event
 *      Timing</a>
 * @author jy95
 * @since 2.1.8
 */
public class EventTiming_pt extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            { "MORN", "durante a manhã" },
            { "MORN.early", "durante o início da manhã" },
            { "MORN.late", "durante a manhã tardia" },
            { "NOON", "cerca de 12:00 PM" },
            { "AFT", "durante a tarde" },
            { "AFT.early", "durante o início da tarde" },
            { "AFT.late", "durante o final da tarde" },
            { "EVE", "durante a noite" },
            { "EVE.early", "durante o início da noite" },
            { "EVE.late", "durante a tarde" },
            { "NIGHT", "durante a noite" },

            { "PHS", "após o sono" },
            { "IMD", "uma única vez" },
            { "HS", "antes de dormir" },
            { "WAKE", "ao acordar" },

            // Meal-related timings
            { "C", "em uma refeição" },
            { "CM", "ao pequeno-almoço" },
            { "CD", "no almoço" },
            { "CV", "ao jantar" },

            { "AC", "antes da refeição" },
            { "ACM", "antes do pequeno-almoço" },
            { "ACD", "antes do almoço" },
            { "ACV", "antes do jantar" },

            { "PC", "após a refeição" },
            { "PCM", "após o pequeno-almoço" },
            { "PCD", "após o almoço" },
            { "PCV", "após o jantar" },
    };

    // English version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
