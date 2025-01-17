package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.BoundsDuration;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.QuantityToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsDuration"
 *
 * @author jy95
 */
public class BoundsDurationR4 implements BoundsDuration<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "boundsDuration" translation. */
    protected final MessageFormat boundsDurationMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code BoundsDurationR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public BoundsDurationR4(FDSConfigR4 config, ResourceBundle bundle) {
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
        return QuantityToStringR4
                .getInstance()
                .convert(bundle, config, boundsDuration)
                .thenApplyAsync((durationText) -> boundsDurationMsg.format(new Object[]{durationText}));
    }
}
