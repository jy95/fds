package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Spanish (es) resource bundle for event timing codes.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event
 *      Timing</a>
 * @author jy95
 * @since 2.1.6
 */
public class EventTiming_es extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            { "MORN", "durante la mañana" },
            { "MORN.early", "durante la madrugada" },
            { "MORN.late", "durante la mañana" },
            { "NOON", "alrededor de las 12:00 PM" },
            { "AFT", "durante la tarde" },
            { "AFT.early", "durante la tarde temprana" },
            { "AFT.late", "durante la tarde" },
            { "EVE", "durante la noche" },
            { "EVE.early", "al anochecer" },
            { "EVE.late", "durante la tarde" },
            { "NIGHT", "durante la noche" },

            { "PHS", "después de dormir" },
            { "IMD", "una sola vez" },
            { "HS", "antes de dormir" },
            { "WAKE", "al despertar" },

            // Meal-related timings
            { "C", "en una comida" },
            { "CM", "en el desayuno" },
            { "CD", "Almuerzo" },
            { "CV", "en la cena" },

            { "AC", "antes de comer" },
            { "ACM", "antes del desayuno" },
            { "ACD", "antes del almuerzo" },
            { "ACV", "antes de cenar" },

            { "PC", "después de comer" },
            { "PCM", "después del desayuno" },
            { "PCD", "después del almuerzo" },
            { "PCV", "después de cenar" },
    };

    // Spanish version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
