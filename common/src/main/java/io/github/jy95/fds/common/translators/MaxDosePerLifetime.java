package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "maxDosePerLifetime".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 * @since 1.0.0
 */
public interface MaxDosePerLifetime<C extends FDSConfig, D> extends Translator<C, D> {

    // Key constant for maxDosePerAdministration message
    String KEY_MAX_DOSE_PER_LIFETIME = "fields.maxDosePerLifetime";

    /**
     * MessageFormat instance used for "maxDosePerLifetime" translation
     *
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "maxDosePerLifetime"
     */
    default MessageFormat getMaxDosePerLifetimeMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.maxDosePerLifetime");
        return new MessageFormat(msg, locale);
    }
    
}
