package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.types.AbstractTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

/**
 * R4 class for translating "timing.modifierExtension"
 *
 * @author jy95
 */
public class TimingModifierExtensionR4 extends AbstractTranslator<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code TimingModifierExtensionR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TimingModifierExtensionR4(FDSConfigR4 config) {
        super(config);
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return this
                .getConfig()
                .fromExtensionsToString(
                        dosage.getTiming().getModifierExtension()
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasModifierExtension();
    }
}
