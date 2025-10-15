package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * German (de) resource bundle for event timing codes.
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event Timing</a>
 */
public class EventTiming_de extends ListResourceBundle {
    
    // German version
    @Override
    public Object[][] getContents() {
        return contents;
    }

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        {"MORN", "während des Vormittags"},
        {"MORN.early", "während des frühen Morgens"},
        {"MORN.late", "während des späten Vormittags"},
        {"NOON", "um die Mittagszeit"},
        {"AFT", "am Nachmittag"},
        {"AFT.early", "am frühen Nachmittag"},
        {"AFT.late", "am späten Nachmittag"},
        {"EVE", "während des Abends"},
        {"EVE.early", "während des frühen Abends"},
        {"EVE.late", "während des späten Abends"},
        {"NIGHT", "über Nacht"},
        
        {"PHS", "nach dem Schlafen"},
        {"IMD", "gelegentlich"},
        {"HS", "vor dem Schlafengehen"},
        {"WAKE", "beim Erwachen"},
        
        // Meal-related timings
        {"C", "bei einer Mahlzeit"},
        {"CM", "beim Frühstück"},
        {"CD", "beim Mittagessen"},
        {"CV", "beim Abendessen"},
        
        {"AC", "vor den Mahlzeiten"},
        {"ACM", "vor dem Frühstück"},
        {"ACD", "vor dem Mittagessen"},
        {"ACV", "vor dem Abendessen"},
        
        {"PC", "nach den Mahlzeiten"},
        {"PCM", "nach dem Frühstück"},
        {"PCD", "nach dem Mittagessen"},
        {"PCV", "nach dem Abendessen"},
    };

}
