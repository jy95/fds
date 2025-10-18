package io.github.jy95.fds.common.l10n;
import java.util.ListResourceBundle;

/**
 * German (de) resource bundle for operator and symbol labels.
 *
 * @see <a href="https://www.hl7.org/fhir/valueset-quantity-comparator.html">FHIR Quantity Comparator</a>
 * @author jy95
 * @since 2.0.1
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
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
