package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.dayOfWeek".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface DayOfWeek<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "dayOfWeek" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "dayOfWeek"
     */
    default MessageFormat getDayOfWeekMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.dayOfWeek");
        return new MessageFormat(msg, locale);
    }

}
