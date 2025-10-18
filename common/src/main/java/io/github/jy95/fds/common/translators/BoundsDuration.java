package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.boundsDuration".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface BoundsDuration<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "boundsDuration" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "boundsDuration"
     */
    default MessageFormat getBoundsDurationMsg(ResourceBundle bundle, Locale locale) {
        String msg = bundle.getString("fields.boundsDuration");
        return new MessageFormat(msg, locale);
    }

}
