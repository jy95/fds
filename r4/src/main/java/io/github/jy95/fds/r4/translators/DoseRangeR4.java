package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractDoseRange;
import io.github.jy95.fds.common.types.DoseAndRateKey;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RangeToStringR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Range;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "doseAndRate.doseRange"
 *
 * @author jy95
 */
public class DoseRangeR4 extends AbstractDoseRange<FDSConfigR4, Dosage> {

    /**
     * Instance for translating range to string
     */
    protected final RangeToStringR4 rangeToStringR4;

    /**
     * Constructor for {@code DoseRangeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public DoseRangeR4(FDSConfigR4 config) {
        super(config);
        rangeToStringR4 = new RangeToStringR4();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var doseRange = getConfig()
                .selectDosageAndRateField(dosage.getDoseAndRate(), DoseAndRateKey.DOSE_RANGE);

        return rangeToStringR4
                .convert(getResources(), getConfig(), (Range) doseRange)
                .thenApplyAsync(rangeText -> doseRangeMsg.format(new Object[]{rangeText}));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return getConfig()
                .hasMatchingComponent(dosage, Dosage.DosageDoseAndRateComponent::hasDoseRange);
    }
}
