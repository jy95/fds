package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.TranslatorExtension;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Timing;
import org.hl7.fhir.r4.model.Extension;

import java.util.List;

/**
 * R4 class for translating "timing.modifierExtension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingModifierExtensionR4 
implements TranslatorExtension<Timing, Extension, FDSConfigR4> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Timing data) {
        return data.hasModifierExtension();
    }

    /** {@inheritDoc} */
    @Override
    public List<Extension> getExtension(Timing data) {
        return data.getModifierExtension();
    }
}
