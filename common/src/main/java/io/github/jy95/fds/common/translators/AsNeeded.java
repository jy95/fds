package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.Translator;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * Interface for translating fields related to the "as needed" / "as needed for" concepts .
 *
 * @param <C> the type of configuration, which must extend {@link io.github.jy95.fds.common.config.FDSConfig}
 * @param <D> the type of data to be translated
 * @author jy95
 */
public interface AsNeeded<C extends FDSConfig, D> extends Translator<C, D> {

    /**
     * MessageFormat instance used for "asNeededFor" translation
     * @param bundle The bundle to extract the key
     * @param locale The locale for the message
     * @return The message template for "asNeededFor"
     */
    default MessageFormat getAsNeededForMsg(ResourceBundle bundle, Locale locale) {
        var msg = bundle.getString("fields.asNeededFor");
        return new MessageFormat(msg, locale);
    }

    /**
     * The message for "asNeeded"
     * @param bundle The bundle to extract the key
     * @return The message template for "asNeeded"
     */
    default String getAsNeededMsg(ResourceBundle bundle) {
        return bundle.getString("fields.asNeeded");
    }

    /**
     * Check if "as needed" is expressed with CodeableConcept ("asNeededFor" / "asNeededCodeableConcept")
     *
     * @param dosage The dosage to check
     * @return true if it is the case, otherwise false
     */
    boolean hasCodeableConcepts(D dosage);

    /**
     * Turn CodeableConcept(s) to a human-readable string
     *
     * @param dosage the dosage field to be converted
     * @return a {@link java.util.concurrent.CompletableFuture} that will complete with the human-readable string
     */
    CompletableFuture<String> convertCodeableConcepts(D dosage);
}
