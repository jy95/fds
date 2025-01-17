package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.AsNeeded;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "asNeededBoolean" / "asNeededCodeableConcept".
 *
 * @author jy95
 */
public class AsNeededR4 implements AsNeeded<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "asNeededFor" translation. */
    protected final MessageFormat asNeededForMsg;
    /** The message for "asNeeded". */
    protected final String asNeededMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code AsNeededR4}.
     *
     * @param config The configuration object used for translation.
     */
    public AsNeededR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.asNeededForMsg = getAsNeededForMsg(bundle, config.getLocale());
        this.asNeededMsg = getAsNeededMsg(bundle);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {

        // Complex case - "as-need" for ...
        if (hasCodeableConcepts(dosage)) {
            return convertCodeableConcepts(dosage);
        }

        // Simple case - only "as-needed"
        return CompletableFuture.supplyAsync(() -> asNeededMsg);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasAsNeeded();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasCodeableConcepts(Dosage dosage) {
        return dosage.hasAsNeededCodeableConcept();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertCodeableConcepts(Dosage dosage) {
        var code = dosage.getAsNeededCodeableConcept();
        var codeAsText = config
                .fromCodeableConceptToString(code);

        return codeAsText
                .thenApplyAsync(v -> ListToString.convert(bundle, List.of(v)))
                .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
    }
}
