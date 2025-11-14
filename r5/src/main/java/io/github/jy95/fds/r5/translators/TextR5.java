package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.types.Translator;
import org.hl7.fhir.r5.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R5 class for translating "text"
 *
 * @author jy95
 */
public class TextR5 implements Translator<Dosage> {

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
