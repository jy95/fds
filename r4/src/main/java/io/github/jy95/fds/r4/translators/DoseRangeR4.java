package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.DoseRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseRange"
 *
 * @author jy95
 */
public class DoseRangeR4 implements DoseRange<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "doseRange" translation. */
    protected final MessageFormat doseRangeMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code DoseRangeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DoseRangeR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.doseRangeMsg = getDoseRangeMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseRange = config
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_RANGE);

        return RangeToStringR4
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
