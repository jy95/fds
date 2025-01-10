package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslator;

/**
 * An abstract class for translating "maxDosePerPeriod".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractMaxDosePerPeriod<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    /** MessageFormat instance used for "maxDosePerPeriod" translation */
    protected final MessageFormat maxDosePerPeriodMsg;

    /**
     * Constructor for {@code AbstractMaxDosePerPeriod}.
     * @param config The configuration object used for translation.
     */
    public AbstractMaxDosePerPeriod(C config) {
        super(config);
        String msg = getResources().getString("fields.maxDosePerPeriod");
        maxDosePerPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }

}
