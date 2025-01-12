package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

import java.util.concurrent.CompletableFuture;

/**
 * Abstract class for translating fields related to the "as needed" / "as needed for" concepts .
 *
 * @param <C> the type of configuration, which must extend {@link FDSConfig}
 * @param <D> the type of data to be translated
 */
public abstract class AbstractAsNeeded<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "asNeededFor" translation. */
    protected final MessageFormat asNeededForMsg;
    /** The message for "asNeeded". */
    protected final String asNeededMsg;

    /**
     * Constructor for {@code AbstractAsNeeded}.
     * @param config The configuration object used for translation.
     */
    public AbstractAsNeeded(C config) {
        super(config);
        var bundle = getResources();
        var msg = bundle.getString("fields.asNeededFor");
        asNeededForMsg = new MessageFormat(msg, this.getConfig().getLocale());
        asNeededMsg = bundle.getString("fields.asNeeded");
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {

        // Complex case - "as-need" for ...
        if (hasCodeableConcepts(dosage)) {
            return convertCodeableConcepts(dosage);
        }

        // Simple case - only "as-needed"
        return CompletableFuture.supplyAsync(() -> asNeededMsg);
    }

    /**
     * Check if "as needed" is expressed with CodeableConcept ("asNeededFor" / "asNeededCodeableConcept")
     * @param dosage The dosage to check
     * @return true if it is the case, otherwise false
     */
    protected abstract boolean hasCodeableConcepts(D dosage);

    /**
     * Turn CodeableConcept(s) to a human-readable string
     * @param dosage the dosage field to be converted
     * @return a {@link CompletableFuture} that will complete with the human-readable string
     */
    protected abstract CompletableFuture<String> convertCodeableConcepts(D dosage);
}
