package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "text"
 *
 * @author jy95
 */
public class TextR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code TextR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TextR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getText);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasText();
    }
}
