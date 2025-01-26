package io.github.jy95.fds.common.functions;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Interface for formatting DateTime objects into human-readable strings.
 *
 * @param <T> The type of the DateTime object to be formatted.
 * @author jy95
 */
public interface FormatDateTimes<T> {

    /**
     * Converts a single date or time object to a human-readable string using the specified locale.
     *
     * @param locale The locale to use for formatting.
     * @param date   The date or time object to format.
     * @return A human-readable string representation of the date or time.
     */
    String convert(Locale locale, T date);

    /**
     * Converts multiple date or time objects to human-readable strings using the specified locale.
     *
     * @param locale The locale to use for formatting.
     * @param dates  The date or time objects to format.
     * @return A list of human-readable string representations of the dates or times.
     */
    default List<String> convert(Locale locale, T... dates) {
        return Arrays
                .stream(dates)
                .map(date -> convert(locale, date))
                .collect(Collectors.toList());
    }
}
