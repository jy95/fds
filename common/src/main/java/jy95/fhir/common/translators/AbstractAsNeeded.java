package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

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
}
