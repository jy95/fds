package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.translators.MaxDosePerPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.RatioToStringR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "maxDosePerPeriod"
 *
 * @author jy95
 */
public class MaxDosePerPeriodR4 implements MaxDosePerPeriod<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "maxDosePerPeriod" translation */
    protected final MessageFormat maxDosePerPeriodMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code MaxDosePerPeriodR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MaxDosePerPeriodR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.config = config;
        this.maxDosePerPeriodMsg = getMaxDosePerPeriodMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratio = dosage.getMaxDosePerPeriod();

        return RatioToStringR4
                .getInstance()
                .convert(bundle, config, ratio)
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
