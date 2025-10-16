package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * German (de) resource bundle for operator and symbol labels.
 * @see <a href="https://www.hl7.org/fhir/valueset-quantity-comparator.html">FHIR Quantity Comparator</a>
 */
public class QuantityComparator_de extends ListResourceBundle {
    
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

    // German version
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
