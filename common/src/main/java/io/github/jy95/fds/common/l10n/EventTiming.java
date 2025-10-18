package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * English (en) resource bundle for event timing codes.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-event-timing.html">FHIR Event Timing</a>
 * @author jy95
 * @since 2.0.1
 */
public class EventTiming extends ListResourceBundle {
    
    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        {"MORN", "during the morning"},
        {"MORN.early", "during the early morning"},
        {"MORN.late", "during the late morning"},
        {"NOON", "around 12:00 PM"},
        {"AFT", "during the afternoon"},
        {"AFT.early", "during the early afternoon"},
        {"AFT.late", "during the late afternoon"},
        {"EVE", "during the evening"},
        {"EVE.early", "during the early evening"},
        {"EVE.late", "during the late evening"},
        {"NIGHT", "during the night"},
        
        {"PHS", "after sleep"},
        {"IMD", "a single time"},
        {"HS", "before sleep"},
        {"WAKE", "upon waking"},
        
        // Meal-related timings
        {"C", "at a meal"},
        {"CM", "at breakfast"},
        {"CD", "at lunch"},
        {"CV", "at dinner"},
        
        {"AC", "before meal"},
        {"ACM", "before breakfast"},
        {"ACD", "before lunch"},
        {"ACV", "before dinner"},
        
        {"PC", "after meal"},
        {"PCM", "after breakfast"},
        {"PCD", "after lunch"},
        {"PCV", "after dinner"},
    };

    // English version
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
