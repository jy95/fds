package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "extension"
 *
 * @author jy95
 */
public class ExtensionR4 implements Translator<FDSConfigR4, Dosage> {

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /**
     * Constructor for {@code ExtensionR4}.
     *
     * @param config The configuration object used for translation.
     */
    public ExtensionR4(FDSConfigR4 config) {
        this.config = config;
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return config
                .fromExtensionsToString(
                        dosage.getExtension()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasExtension();
    }
}
