package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.boundsRange".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface DoseRange<C extends FDSConfig, D> extends Translator<C, D> {

    /**
     * MessageFormat instance used for "doseRange" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "doseRange"
     */
    default MessageFormat getDoseRangeMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.doseRange");
        return new MessageFormat(msg, locale);
    }
    
}
