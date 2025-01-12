package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "site"
 */
public class SiteR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code SiteR4}.
     * @param config The configuration object used for translation.
     */
    public SiteR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .getFromCodeableConceptToString()
                .apply(
                        dosage.getSite()
                );
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasSite();
    }
}
