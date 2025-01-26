package io.github.jy95.fds.r5.functions;

import io.github.jy95.fds.common.functions.FormatDateTimes;
import org.hl7.fhir.r5.model.DateTimeType;
import org.hl7.fhir.utilities.DateTimeUtil;

import java.util.Locale;

/**
 * Singleton implementation for formatting FHIR R5 DateTimeType objects
 * into human-readable strings. Implements the Bill Pugh Singleton pattern.
 * This class ensures thread-safe lazy initialization of the singleton instance.
 *
 * @author jy95
 */
public class FormatDateTimesR5 implements FormatDateTimes<DateTimeType> {

    // Private constructor to prevent instantiation
    private FormatDateTimesR5() {}

    // Static inner class responsible for holding the Singleton instance
    private static class Holder {
        private static final FormatDateTimesR5 INSTANCE = new FormatDateTimesR5();
    }

    /**
     * Returns the singleton instance of FormatDateTimesR5.
     *
     * @return the singleton instance
     */
    public static FormatDateTimesR5 getInstance() {
        return Holder.INSTANCE;
    }
    /** {@inheritDoc} */
    @Override
    public String convert(Locale locale, DateTimeType date) {
        return DateTimeUtil.toHumanDisplay(
                locale,
                date.getTimeZone(),
                date.getPrecision(),
                date.getValue()
        );
    }
    
}
