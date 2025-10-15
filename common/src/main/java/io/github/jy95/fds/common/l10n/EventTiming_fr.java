package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * French (fr) resource bundle for event timing codes.
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event Timing</a>
 */
public class EventTiming_fr extends ListResourceBundle {
    
    // French version
    @Override
    public Object[][] getContents() {
        return contents;
    }

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        {"MORN", "durant le matin"},
        {"MORN.early", "en début de matinée"},
        {"MORN.late", "en fin de matinée"},
        {"NOON", "autour de midi"},
        {"AFT", "durant l'après-midi"},
        {"AFT.early", "en début d'après-midi"},
        {"AFT.late", "en fin d'après-midi"},
        {"EVE", "en soirée"},
        {"EVE.early", "en début de soirée"},
        {"EVE.late", "en fin de soirée"},
        {"NIGHT", "durant la nuit"},
        
        {"PHS", "après le sommeil"},
        {"IMD", "une seule fois"},
        {"HS", "avant le sommeil"},
        {"WAKE", "au réveil"},
        
        // Meal-related timings
        {"C", "durant un repas"},
        {"CM", "au petit-déjeuner"},
        {"CD", "au déjeuner"},
        {"CV", "au dinner"},
        
        {"AC", "avant le repas"},
        {"ACM", "avant le petit-déjeuner"},
        {"ACD", "avant le déjeuner"},
        {"ACV", "avant le dinner"},
        
        {"PC", "après le repas"},
        {"PCM", "après le petit-déjeuner"},
        {"PCD", "après le déjeuner"},
        {"PCV", "après le dinner"},
    };
}
