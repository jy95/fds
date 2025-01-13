package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "method"
 *
 * @author jy95
 */
public class MethodR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code MethodR4}.
     *
     * @param config The configuration object used for translation.
     */
    public MethodR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .getFromCodeableConceptToString()
                .apply(
                        dosage.getMethod()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasMethod();
    }
}
