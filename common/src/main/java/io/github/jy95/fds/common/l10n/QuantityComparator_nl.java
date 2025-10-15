package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * Dutch (nl) resource bundle for operator and symbol labels.
 * @see <a href="https://www.hl7.org/fhir/valueset-quantity-comparator.html">FHIR Quantity Comparator</a>
 */
public class QuantityComparator_nl extends ListResourceBundle {
    
    // Dutch version
    @Override
    public Object[][] getContents() {
        return contents;
    }

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
}
