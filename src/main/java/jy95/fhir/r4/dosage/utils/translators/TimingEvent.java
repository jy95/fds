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

    // Translations
    private final MessageFormat eventMsg;

    public TimingEvent(FDUConfig config) {
        super(config);
        var msg = getResources().getString("fields.event");
        eventMsg = new MessageFormat(msg, this.getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var bundle = this.getResources();

            DateTimeType[] events = dosage.getTiming().getEvent().toArray(DateTimeType[]::new);
            List<String> eventsList = FormatDateTimes.convert(this.getConfig().getLocale(), events);
            String eventsAsString = ListToString.convert(bundle, eventsList);

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "eventCondition", eventsList.size(),
                    "event", eventsAsString
            );

            // Format the message with the named arguments
            return eventMsg.format(arguments);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasEvent();
    }
}
