package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

/**
 * An abstract class for translating "timing.repeat.boundsPeriod".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractBoundsPeriod<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    /** MessageFormat instance used for "boundsPeriod" translation. */
    protected final MessageFormat boundsPeriodMsg;

    /**
     * Constructor for {@code AbstractBoundsPeriod}.
     * @param config The configuration object used for translation.
     */
    public AbstractBoundsPeriod(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsPeriod");
        boundsPeriodMsg = new MessageFormat(msg, getConfig().getLocale());
    }
}
