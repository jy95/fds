package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.rateRatio".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractRateRatio<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "rateRatio" translation. */
    protected final MessageFormat rateRatioMsg;

    /**
     * Constructor for {@code AbstractRateRatio}.
     * @param config The configuration object used for translation.
     */
    public AbstractRateRatio(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateRatio");
        rateRatioMsg = new MessageFormat(msg, locale);
    }
    
}
