package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Interface for translating "doseAndRate.doseQuantity".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public interface DoseQuantity<C extends FDSConfig, D> extends Translator<C, D> {

    /**
     * MessageFormat instance used for "doseQuantity" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "doseQuantity"
     */
    default MessageFormat getDoseQuantityMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.doseQuantity");
        return new MessageFormat(msg, locale);
    }
}
