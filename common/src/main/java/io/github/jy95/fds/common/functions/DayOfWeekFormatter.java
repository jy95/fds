package io.github.jy95.fds.common.functions;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.util.Calendar;

import lombok.*;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class to format day of week
 *
 * @author jy95
 * @since 1.0.2
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
     * Cache for storing localized DateFormatSymbols instances.
     * Locale -> longWeekDays String array
     */
    private static final Map<Locale, String[]> WEEKDAYS_CACHE = new ConcurrentHashMap<>();

    /**
     * Retrieves the long weekday names array for the given locale, using the cache.
     * If the array is not in the cache, it computes it and stores it.
     *
     * @param locale The locale to use
     * @return The array of long weekday names (index 1=Sunday, 7=Saturday)
     */
    private static String[] getLongWeekDays(Locale locale) {
        // Use computeIfAbsent for thread-safe caching and calculation
        return WEEKDAYS_CACHE.computeIfAbsent(locale, loc -> 
            DateFormatSymbols
                .getInstance(loc)
                .getWeekdays()
        );
    }

    /**
     * Translates a single-day code into its corresponding day of the week in text,
     * utilizing the specified locale.
     *
     * @param code The <a href="https://build.fhir.org/valueset-days-of-week.html">Day Of Week</a> code to translate
     * @param locale The locale to use for translation
     * @return the translated day of the week as a string.
     */
    public static String codeToLongText(@NonNull String code, @NonNull Locale locale) {
        var dayIndex = DAY_MAPPING.get(code);
        var longWeekDays = getLongWeekDays(locale);
        return longWeekDays[dayIndex];
    }   
}
