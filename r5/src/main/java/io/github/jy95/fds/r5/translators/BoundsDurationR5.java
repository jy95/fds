package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.BoundsDuration;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.QuantityToStringR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
public class BoundsDurationR5 implements BoundsDuration<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "boundsDuration" translation. */
    protected final MessageFormat boundsDurationMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code BoundsDurationR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public BoundsDurationR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.boundsDurationMsg = getBoundsDurationMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsDuration();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsDuration = dosage.getTiming().getRepeat().getBoundsDuration();
        return QuantityToStringR5
                .getInstance()
                .convert(bundle, config, boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
