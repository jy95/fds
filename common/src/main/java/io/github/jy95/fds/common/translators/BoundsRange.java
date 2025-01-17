package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.boundsRange".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface BoundsRange<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "boundsRange" translation.
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "boundsRange"
     */
    default MessageFormat getBoundsRangeMsg(ResourceBundle bundle, Locale locale) {
        String msg = bundle.getString("fields.boundsRange");
        return new MessageFormat(msg, locale);
    }
}
