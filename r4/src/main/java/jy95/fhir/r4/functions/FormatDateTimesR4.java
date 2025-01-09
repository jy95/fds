package jy95.fhir.r4.functions;

import java.util.Locale;

import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.utilities.DateTimeUtil;

import jy95.fhir.common.functions.AbstractFormatDateTimes;

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
