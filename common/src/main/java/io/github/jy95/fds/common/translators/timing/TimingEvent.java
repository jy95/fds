package io.github.jy95.fds.common.translators.timing;

import io.github.jy95.fds.common.types.Translator;
import java.util.List;

/**
 * Interface for translating "timing.event".
 *
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface TimingEvent<D> extends Translator<D> {

    /**
     * Key constant for event message
     */
    String KEY_EVENT = "fields.event";

    /**
     * Extracts a list of timing events from the data object.
     *
     * @param dosage The data object containing timing events.
     * @return A list of timing events represented as strings.
     */
    List<String> getEvents(D dosage);
}
