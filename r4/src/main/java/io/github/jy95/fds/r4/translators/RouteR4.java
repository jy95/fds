package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "route"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class RouteR4 implements Translator<FDSConfigR4, Dosage> {

    /**
     * The configuration object used by this API.
     */
    private final FDSConfigR4 config;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return config
                .fromCodeableConceptToString(
                        dosage.getRoute()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasRoute();
    }
}
