package io.github.jy95.fds.common.functions;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.Calendar;

import java.util.Locale;
import java.util.Map;

/**
 * Class to format day of week
 */
public final class DayOfWeekFormatter {

    /**
     * Map each code of <a href="https://build.fhir.org/valueset-days-of-week.html">Day Of Week</a> to their ICU4J values
     */
    private static final Map<String, Integer> DAY_MAPPING = Map.ofEntries(
            Map.entry("mon", Calendar.MONDAY),
            Map.entry("tue", Calendar.TUESDAY),
            Map.entry("wed", Calendar.WEDNESDAY),
            Map.entry("thu", Calendar.THURSDAY),
            Map.entry("fri", Calendar.FRIDAY),
            Map.entry("sat", Calendar.SATURDAY),
            Map.entry("sun", Calendar.SUNDAY)
    );

    /**
     * Bundle that contains translations
     */
    private final String[] longWeekDays;

    /**
     * Constructor for the formatter
     * @param locale The locale to use
     */
    public DayOfWeekFormatter(Locale locale) {
        longWeekDays = DateFormatSymbols
                .getInstance(locale)
                .getWeekdays();
    }

    /**
     * Translates a single-day code into its corresponding day of the week in text.
     * @param code The <a href="https://build.fhir.org/valueset-days-of-week.html">Day Of Week</a> code to translate
     * @return the translated day of the week as a string.
     */
    public String codeToLongText(String code) {
        return longWeekDays[DAY_MAPPING.get(code)];
    }
}
