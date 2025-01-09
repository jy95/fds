package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.types.AbstractTranslatorTiming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractTimingEvent<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    protected final MessageFormat timingEventMsg;

    public AbstractTimingEvent(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.event");
        timingEventMsg = new MessageFormat(msg, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var bundle = this.getResources();
            var eventsList = getEvents(dosage);

            String eventsAsString = ListToString.convert(bundle, eventsList);

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "eventCondition", eventsList.size(),
                    "event", eventsAsString
            );

            // Format the message with the named arguments
            return timingEventMsg.format(arguments);
        });
    }

    protected abstract List<String> getEvents(D dosage);
}
