package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.TimingEvent;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.event"
 *
 * @author jy95
 */
public class TimingEventR4 implements TimingEvent<FDSConfigR4, Dosage> {

    private final FormatDateTimesR4 formatDateTimesR4;

    // Translations
    /** MessageFormat instance used for "event" translation. */
    protected final MessageFormat timingEventMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code TimingEventR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TimingEventR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        formatDateTimesR4 = new FormatDateTimesR4();
        this.timingEventMsg = getTimingEventMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasEvent();
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getEvents(Dosage dosage) {
        DateTimeType[] events = dosage.getTiming().getEvent().toArray(DateTimeType[]::new);
        return formatDateTimesR4.convert(config.getLocale(), events);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
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
}
