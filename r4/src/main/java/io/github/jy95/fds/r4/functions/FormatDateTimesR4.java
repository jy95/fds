package io.github.jy95.fds.r4.functions;

import java.util.Locale;

import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.utilities.DateTimeUtil;

import io.github.jy95.fds.common.functions.AbstractFormatDateTimes;

/**
 * R4 class for formatting DateTime objects into human-readable strings
 */
public class FormatDateTimesR4 extends AbstractFormatDateTimes<DateTimeType> {

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
