package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.TranslatorExtension;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Extension;

import java.util.List;

/**
 * R4 class for translating "timing.repeat.extension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingRepeatExtensionR4 
implements TranslatorExtension<Dosage, Extension, FDSConfigR4> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasExtension();
    }

    /** {@inheritDoc} */
    @Override
    public List<Extension> getExtension(Dosage dosage) {
        return dosage.getTiming().getRepeat().getExtension();
    }
}
