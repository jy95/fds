package io.github.jy95.fds.r5.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.TranslatorExtension;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hl7.fhir.r5.model.Dosage;
import org.hl7.fhir.r5.model.Extension;

import java.util.List;

/**
 * R5 class for translating "timing.extension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class TimingExtensionR5 
implements TranslatorExtension<Dosage, Extension, FDSConfigR5> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR5> translationService;

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasExtension();
    }

    /** {@inheritDoc} */
    @Override
    public List<Extension> getExtension(Dosage dosage) {
        return dosage.getTiming().getExtension();
    }
}
