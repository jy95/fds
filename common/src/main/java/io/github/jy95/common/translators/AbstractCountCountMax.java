package io.github.jy95.common.translators;

import com.ibm.icu.text.MessageFormat;

import io.github.jy95.common.config.FDSConfig;
import io.github.jy95.common.types.AbstractTranslatorTiming;

import java.util.Map;

/**
 * An abstract class for translating "timing.repeat.count" / "timing.repeat.countMax".
 *
 * @param <C> The type of configuration, extending {@link FDSConfig}.
 * @param <D> The type of the translated data.
 */
public abstract class AbstractCountCountMax<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    /** MessageFormat instance used for "count" &amp; "countMax" translation */
    protected final MessageFormat countMaxMsg;
    /** MessageFormat instance used for "count" translation. */
    protected final MessageFormat countMsg;

    /**
     * Constructor for {@code AbstractCountCountMax}.
     * @param config The configuration object used for translation.
     */
    public AbstractCountCountMax(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.countMax");
        var msg2 = bundle.getString("fields.count");
        countMaxMsg = new MessageFormat(msg1, locale);
        countMsg = new MessageFormat(msg2, locale);
    }

    /**
     * Turns the "count" and "countMax" values into a formatted text.
     *
     * @param countMin the minimum count value.
     * @param countMax the maximum count value.
     * @return the formatted string representing the "count" and "countMax" values.
     */
    protected String turnCountAndCountMaxToText(int countMin, int countMax) {
        Map<String, Object> arguments = Map.of(
                "minCount", countMin,
                "maxCount", countMax
        );
        return countMaxMsg.format(arguments);
    }

    /**
     * Turns the "count" value into a formatted text.
     *
     * @param count the count value to be formatted.
     * @return the formatted string representing the "count" value.
     */
    protected String turnCountToText(int count) {
        return countMsg.format(new Object[]{count});
    }
}
