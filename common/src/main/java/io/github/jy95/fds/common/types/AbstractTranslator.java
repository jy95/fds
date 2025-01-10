package io.github.jy95.fds.common.types;

import lombok.Getter;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import io.github.jy95.fds.common.config.FDSConfig;

/**
 * Abstract class for translating dosage fields into human-readable representations.
 * This class provides base functionality for configuration and resource bundle handling,
 * while requiring subclasses to define specific translation behavior.
 *
 * @param <C> the type of configuration extending {@link FDSConfig}
 * @param <D> the type of dosage field to be translated
 */
@Getter
public abstract class AbstractTranslator<C extends FDSConfig, D> {

    /**
     * The configuration object used for the translator.
     */
    private final C config;
    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle resources;

    /**
     * Constructs a new {@code AbstractTranslator} with the specified configuration.
     * @param config the configuration object providing locale and resource bundle
     */
    public AbstractTranslator(C config) {
        this.config = config;
        this.resources = config.getSelectResourceBundle().apply(config.getLocale());
    }

    /**
     * Converts a dosage field into a human-readable string representation asynchronously.
     *
     * @param dosage the dosage field to be converted
     * @return a {@link CompletableFuture} that will complete with the human-readable string
     */
    public abstract CompletableFuture<String> convert(D dosage);

    /**
     * Checks whether a dosage field is present and can be converted to a string.
     *
     * @param dosage the dosage field to check
     * @return {@code true} if the dosage field is present, {@code false} otherwise
     */
    public boolean isPresent(D dosage) {
        return false;
    }
}
