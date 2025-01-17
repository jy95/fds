package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.RateRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.rateRange"
 *
 * @author jy95
 */
public class RateRangeR4 implements RateRange<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "rateRange" translation. */
    protected final MessageFormat rateRangeMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code RateRangeR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public RateRangeR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.rateRangeMsg = getRateRangeMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var rateRange = config
                .selectDosageAndRateField(
                        dosage.getDoseAndRate(),
                        DoseAndRateKey.RATE_RANGE
                );

        return RangeToStringR4
                .getInstance()
                .convert(bundle, config, (Range) rateRange)
                .thenApplyAsync(rateRatioText -> rateRangeMsg.format(new Object[]{rateRatioText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return config
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasRateRange);
    }
}
