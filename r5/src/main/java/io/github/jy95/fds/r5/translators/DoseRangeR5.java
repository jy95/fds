package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.DoseRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RangeToStringR5;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Range;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "doseAndRate.doseRange"
 *
 * @author jy95
 */
public class DoseRangeR5 implements DoseRange<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "doseRange" translation. */
    protected final MessageFormat doseRangeMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DoseRangeR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public DoseRangeR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.doseRangeMsg = getDoseRangeMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseRange = config
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_RANGE);

        return RangeToStringR5
                .getInstance()
                .convert(bundle, config, (Range) doseRange)
                .thenApplyAsync(rangeText -> doseRangeMsg.format(new Object[]{rangeText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return config
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseRange);
    }
}
