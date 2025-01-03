package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.FormatDateTimes;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class BoundsPeriod extends AbstractTranslator {

    public BoundsPeriod(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
            var hasStart = boundPeriods.hasStart();
            var hasEnd = boundPeriods.hasEnd();
            var bundle = this.getResources();
            var locale = this.getConfig().getLocale();
            var msg = bundle.getString("fields.boundsPeriod");

            // Prepare date values using FormatDateTimes.convert()
            String startDate = hasStart ? FormatDateTimes.convert(locale, boundPeriods.getStartElement()) : "";
            String endDate = hasEnd ? FormatDateTimes.convert(locale, boundPeriods.getEndElement()) : "";

            // Use ICU MessageFormat for more flexible and locale-sensitive formatting
            MessageFormat messageFormat = new MessageFormat(msg, this.getConfig().getLocale());

            // Choose the correct condition based on the presence of start and end dates
            String condition = hasStart && hasEnd ? "0" : (hasStart ? "1" : "2");

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "startDate", startDate,
                    "endDate", endDate,
                    "condition", condition
            );

            // Format the message with the named arguments
            return messageFormat.format(arguments);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }
}
