package io.github.jy95.fds.r4.functions;

import io.github.jy95.fds.common.functions.FormatDateTimes;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.utilities.DateTimeUtil;

import java.util.Locale;

/**
 * R4 class for formatting DateTime objects into human-readable strings
 *
 * @author jy95
 */
public class FormatDateTimesR4 implements FormatDateTimes<DateTimeType> {

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
