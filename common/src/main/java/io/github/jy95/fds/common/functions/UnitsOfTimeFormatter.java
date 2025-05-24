package io.github.jy95.fds.common.functions;

import com.ibm.icu.number.NumberFormatter;
import com.ibm.icu.number.Precision;
import com.ibm.icu.number.UnlocalizedNumberFormatter;
import com.ibm.icu.util.MeasureUnit;

import java.util.Locale;
import java.util.Map;

/**
 * Class to format units of time
 *
 * @author jy95
 */
public final class UnitsOfTimeFormatter {

    /**
     * Map each code of <a href="https://build.fhir.org/valueset-units-of-time.html">Units of Time</a> to their ICU4J units
     */
    private static final Map<String, MeasureUnit> UNIT_MAPPING = Map.ofEntries(
            Map.entry("ms", MeasureUnit.MILLISECOND),
            Map.entry("s", MeasureUnit.SECOND),
            Map.entry("min", MeasureUnit.MINUTE),
            Map.entry("h", MeasureUnit.HOUR),
            Map.entry("d", MeasureUnit.DAY),
            Map.entry("wk", MeasureUnit.WEEK),
            Map.entry("mo", MeasureUnit.MONTH),
            Map.entry("a", MeasureUnit.YEAR)
    );

    /**
     * Common formatter for all calls to this class
     */
    private static final UnlocalizedNumberFormatter formatter = NumberFormatter
            .with()
            .unitWidth(NumberFormatter.UnitWidth.FULL_NAME);

    /**
     * No constructor for this class
     */
    private UnitsOfTimeFormatter() {}

    /**
     * Formats a time unit with a count (e.g., "3 heures", "1 minute").
     *
     * @param locale The locale expected for the resulting text
     * @param unit The <a href="https://build.fhir.org/valueset-units-of-time.html">unit code</a>
     * @param count The quantity
     * @return A formatted time-unit string with a count
     */
    public static String formatWithCount(Locale locale, String unit, Number count) {
        return formatter
                .locale(locale)
                .unit(UNIT_MAPPING.get(unit))
                .format(count)
                .toString();
    }

    /**
     * Formats a time unit without a count (e.g., "heures", "minute").
     *
     * @param locale The locale expected for the resulting text
     * @param unit The <a href="https://build.fhir.org/valueset-units-of-time.html">unit code</a>
     * @param count The quantity
     * @return A formatted time-unit string without a count
     */
    public static String formatWithoutCount(Locale locale, String unit, Number count) {
        // ICU4j doesn't have a method for extracting only the unit with plural form so a bit of magic here
        return formatter
                .unit(UNIT_MAPPING.get(unit))
                .locale(locale)
                .precision(Precision.maxSignificantDigits(1))
                .format(count)
                .toString()
                .substring(2); // 1 because of the number display + 1 because of spacing
    }
}
