package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.rateRange".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractRateRange<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    /** MessageFormat instance used for "rateRange" translation. */
    protected final MessageFormat rateRangeMsg;

    /**
     * Constructor for {@code AbstractRateRange}.
     *
     * @param config The configuration object used for translation.
     */
    public AbstractRateRange(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateRange");
        rateRangeMsg = new MessageFormat(msg, locale);
    }
}
