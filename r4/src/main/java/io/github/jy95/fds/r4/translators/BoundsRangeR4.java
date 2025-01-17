package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.BoundsRange;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.repeat.boundsRange"
 *
 * @author jy95
 */
public class BoundsRangeR4 implements BoundsRange<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "boundsRange" translation. */
    protected final MessageFormat boundsRangeMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code BoundsRangeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public BoundsRangeR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.boundsRangeMsg = getBoundsRangeMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var boundsRange = dosage.getTiming().getRepeat().getBoundsRange();

        return RangeToStringR4
                .getInstance()
                .convert(bundle, config, boundsRange)
                .thenApplyAsync(v -> boundsRangeMsg.format(new Object[]{v}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsRange();
    }
}
