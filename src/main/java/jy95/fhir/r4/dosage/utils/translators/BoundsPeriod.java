package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.FormatDateTimes;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class BoundsPeriod extends AbstractTranslator {

    // Translations
    private final MessageFormat boundsPeriodMsg;

    public BoundsPeriod(FDUConfig config) {
        super(config);
        var msg = getResources().getString("fields.boundsPeriod");
        boundsPeriodMsg = new MessageFormat(msg, this.getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
            var hasStart = boundPeriods.hasStart();
            var hasEnd = boundPeriods.hasEnd();
            var locale = this.getConfig().getLocale();

            // Prepare date values using FormatDateTimes.convert()
            String startDate = hasStart ? FormatDateTimes.convert(locale, boundPeriods.getStartElement()) : "";
            String endDate = hasEnd ? FormatDateTimes.convert(locale, boundPeriods.getEndElement()) : "";

            // Choose the correct condition based on the presence of start and end dates
            String condition = hasStart && hasEnd ? "0" : (hasStart ? "1" : "other");

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "startDate", startDate,
                    "endDate", endDate,
                    "condition", condition
            );

            // Format the message with the named arguments
            return boundsPeriodMsg.format(arguments);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }
}
