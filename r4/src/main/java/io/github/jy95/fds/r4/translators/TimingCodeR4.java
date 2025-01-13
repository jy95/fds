package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslatorTiming;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.code"
 *
 * @author jy95
 */
public class TimingCodeR4 extends AbstractTranslatorTiming<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code TimingCodeR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TimingCodeR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .fromCodeableConceptToString(
                        dosage.getTiming().getCode()
                );
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasCode();
    }
}
