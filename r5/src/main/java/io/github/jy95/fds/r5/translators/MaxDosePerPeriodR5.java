package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.MaxDosePerPeriod;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.functions.RatioToStringR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * R5 class for translating "maxDosePerPeriod"
 *
 * @author jy95
 */
public class MaxDosePerPeriodR5 implements MaxDosePerPeriod<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "maxDosePerPeriod" translation */
    protected final MessageFormat maxDosePerPeriodMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code MaxDosePerPeriodR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public MaxDosePerPeriodR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.bundle = bundle;
        this.config = config;
        this.maxDosePerPeriodMsg = getMaxDosePerPeriodMsg(bundle, config.getLocale());
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var ratioFutures = dosage
                .getMaxDosePerPeriod()
                .stream()
                .map(ratio -> RatioToStringR5.getInstance().convert(bundle, config, ratio))
                .collect(Collectors.toList());
        
        return CompletableFuture
                .allOf(ratioFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var ratioTexts = ratioFutures
                            .stream()
                            .map(future -> future.getNow(""))
                            .collect(Collectors.toList());
                    return ListToString.convert(bundle, ratioTexts);
                })
                .thenApplyAsync((ratioText) -> maxDosePerPeriodMsg.format(new Object[] { ratioText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMaxDosePerPeriod();
    }
}
