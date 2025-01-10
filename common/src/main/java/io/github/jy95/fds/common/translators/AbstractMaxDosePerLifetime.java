package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

/**
 * An abstract class for translating "maxDosePerLifetime".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractMaxDosePerLifetime<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "maxDosePerLifetime" translation */
    protected final MessageFormat maxDosePerLifetimeMsg;

    /**
     * Constructor for {@code AbstractMaxDosePerLifetime}.
     * @param config The configuration object used for translation.
     */
    public AbstractMaxDosePerLifetime(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg = bundle.getString("fields.maxDosePerLifetime");
        maxDosePerLifetimeMsg = new MessageFormat(msg, locale);
    }
    
}
