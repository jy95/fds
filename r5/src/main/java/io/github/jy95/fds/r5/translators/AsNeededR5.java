package io.github.jy95.fds.r5.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.AsNeeded;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import org.hl7.fhir.r5.model.Dosage;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * R5 class for translating "asNeededBoolean" / "asNeededCodeableConcept".
 *
 * @author jy95
 */
public class AsNeededR5 implements AsNeeded<FDSConfigR5, Dosage> {

    // Translations
    /** MessageFormat instance used for "asNeededFor" translation. */
    protected final MessageFormat asNeededForMsg;
    /** The message for "asNeeded". */
    protected final String asNeededMsg;

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR5 config;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code AsNeededR5}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public AsNeededR5(FDSConfigR5 config, ResourceBundle bundle) {
        this.config = config;
        this.bundle = bundle;
        this.asNeededForMsg = getAsNeededForMsg(bundle, config.getLocale());
        this.asNeededMsg = getAsNeededMsg(bundle);
    }

    /** {@inheritDoc} */
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
        return dosage.hasAsNeededFor();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convertCodeableConcepts(Dosage dosage) {
        var codes = dosage.getAsNeededFor();
        // TODO here
        var codesFutures = codes
                .stream()
                .map(config::fromCodeableConceptToString)
                .collect(Collectors.toList());

        return CompletableFuture
                .allOf(codesFutures.toArray(CompletableFuture[]::new))
                .thenApplyAsync(v -> {
                    var codesAsText = codesFutures
                            .stream()
                            .map(future -> future.getNow(""))
                            .collect(Collectors.toList());

                    return ListToString.convert(bundle, codesAsText);
                })
                .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
    }
}
