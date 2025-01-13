package io.github.jy95.fds.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.types.AbstractTranslatorTiming;

/**
 * An abstract class for translating "timing.repeat.boundsRange".
 *
 * @param <C> The type of configuration, extending {@link io.github.jy95.fds.common.config.FDSConfig}.
 * @param <D> The type of the translated data.
 * @author jy95
 */
public abstract class AbstractBoundsRange<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {

    // Translations
    /** MessageFormat instance used for "boundsRange" translation. */
    protected final MessageFormat boundsRangeMsg;

    /**
     * Constructor for {@code AbstractBoundsRange}.
     *
     * @param config The configuration object used for translation.
     */
    public AbstractBoundsRange(C config) {
        super(config);
        String msg = getResources().getString("fields.boundsRange");
        boundsRangeMsg = new MessageFormat(msg, getConfig().getLocale());
    }
}
