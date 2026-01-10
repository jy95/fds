package io.github.jy95.fds.common.l10n;

import java.util.ListResourceBundle;

/**
 * Portuguese (pt) resource bundle for operator and symbol labels.
 *
 * @see <a href=
 *      "https://www.hl7.org/fhir/valueset-quantity-comparator.html">FHIR
 *      Quantity Comparator</a>
 * @author jy95
 * @since 2.1.8
 */
public class QuantityComparator_pt extends ListResourceBundle {

    // Static content array holding the key-value pairs
    static final Object[][] contents = {
            // Inequality Operators
            { "<", "<" },
            { "<=", "<=" },
            { ">=", ">=" },
            { ">", ">" },

            // Abbreviation
            { "ad", "ad" },
    };

    // Portuguese version
    /** {@inheritDoc} */
    /** {@inheritDoc} */
    @Override
    public Object[][] getContents() {
        return contents;
    }
}
