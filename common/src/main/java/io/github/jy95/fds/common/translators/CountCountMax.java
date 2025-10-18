package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.TranslatorTiming;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "timing.repeat.count" / "timing.repeat.countMax".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface CountCountMax<C extends FDSConfig, D> extends TranslatorTiming<C, D> {

    /**
     * MessageFormat instance used for "count" &amp; "countMax" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "count" &amp; "countMax"
     */
    default MessageFormat getCountMaxMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.countMax");
        return new MessageFormat(msg, locale);
    }

    /**
     * MessageFormat instance used for "count" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "count"
     */
    default MessageFormat getCountMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.count");
        return new MessageFormat(msg, locale);
    }

    /**
     * Check if "timing.repeat.countMax" exists
     *
     * @param dosage the dosage object to check
     * @return True if it is the case, false otherwise
     */
    boolean hasCountMax(D dosage);
}
