package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.TranslatorExtension;
import io.github.jy95.fds.r4.config.FDSConfigR4;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.Extension;

/**
 * R4 class for translating "extension"
 *
 * @author jy95
 */
@RequiredArgsConstructor
public class ExtensionR4 
implements io.github.jy95.fds.common.translators.Extension<Dosage>, TranslatorExtension<Dosage, Extension, FDSConfigR4> {

    /** Translation service */
    @Getter
    private final TranslationService<FDSConfigR4> translationService;

    /** {@inheritDoc} */
    @Override
    public List<Extension> getExtension(Dosage dosage) {
        return dosage.getExtension();
    }

}
