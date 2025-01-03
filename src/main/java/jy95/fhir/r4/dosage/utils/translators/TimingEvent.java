package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.functions.FormatDateTimes;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.DateTimeType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TimingEvent extends AbstractTranslator {

    public TimingEvent(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var bundle = this.getResources();
            var msg = bundle.getString("fields.event");

            DateTimeType[] events = dosage.getTiming().getEvent().toArray(DateTimeType[]::new);
            List<String> eventsList = FormatDateTimes.convert(this.getConfig().getLocale(), events);
            String eventsAsString = ListToString.convert(bundle, eventsList);

            // Use ICU MessageFormat for more flexible and locale-sensitive formatting
            MessageFormat messageFormat = new MessageFormat(msg, this.getConfig().getLocale());

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "eventCondition", eventsList.size(),
                    "event", eventsAsString
            );

            // Format the message with the named arguments
            return messageFormat.format(arguments);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasEvent();
    }
}
