package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.event".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface TimingEvent<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "event" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "event"
     */
    default MessageFormat getTimingEventMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.event");
        return new MessageFormat(msg, locale);
    }

    /**
     * Extracts a list of timing events from the data object.
     *
     * @param dosage The data object containing timing events.
     * @return A list of timing events represented as strings.
     */
    List<String> getEvents(D dosage);
}
