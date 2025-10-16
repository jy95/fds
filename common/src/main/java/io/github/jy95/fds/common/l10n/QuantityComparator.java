package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * English (en) resource bundle for operator and symbol labels.
 * @see <a href="https://www.hl7.org/fhir/valueset-quantity-comparator.html">FHIR Quantity Comparator</a>
 */
public class QuantityComparator extends ListResourceBundle {
    
    // Static content array holding the key-value pairs
    static final Object[][] contents = {
        // Inequality Operators
        {"<", "<"},
        {"<=", "<="},
        {">=", ">="},
        {">", ">"},
        
        // Abbreviation
        {"ad", "ad"},
    };

    // English version
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
