package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * An abstract class for translating "timing.event".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractTimingEvent<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "event" translation. */
    protected final MessageFormat timingEventMsg;

    /**
     * Constructor for {@code AbstractTimingEvent}.
     * @param config The configuration object used for translation.
     */
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

    /**
     * Extracts a list of timing events from the data object.
     *
     * @param dosage The data object containing timing events.
     * @return A list of timing events represented as strings.
     */
    protected abstract List<String> getEvents(D dosage);
}
