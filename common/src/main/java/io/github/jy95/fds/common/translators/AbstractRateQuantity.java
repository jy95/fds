package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

/**
 * An abstract class for translating "doseAndRate.rateQuantity".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractRateQuantity<C extends FDSConfig, D> extends AbstractTranslator<C, D> {
    
    // Translations
    /** MessageFormat instance used for "rateQuantity" translation. */
    protected final MessageFormat rateQuantityMsg;

    /**
     * Constructor for {@code AbstractRateQuantity}.
     *
     * @param config The configuration object used for translation.
     */
    public AbstractRateQuantity(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.rateQuantity");
        rateQuantityMsg = new MessageFormat(msg, locale);
    }
}
