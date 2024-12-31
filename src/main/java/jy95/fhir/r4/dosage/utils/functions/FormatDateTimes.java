package jy95.fhir.r4.dosage.utils.functions;

import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.utilities.DateTimeUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.List;

public class FormatDateTimes {

    public static String convert(Locale locale, DateTimeType date){
        return DateTimeUtil.toHumanDisplay(
                locale,
                date.getTimeZone(),
                date.getPrecision(),
                date.getValue()
        );
    }

    public static List<String> convert(Locale locale, DateTimeType... dates) {
        return  Arrays
                .stream(dates)
                .map(d -> convert(locale, d)).toList();
    }

}
