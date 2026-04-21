package io.github.jy95.fds.common.types;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.CodeableConceptProcessor;
import lombok.RequiredArgsConstructor;

/**
 * A translator for converting CodeableConcept instances to strings.
 *
 * @param <D> The type of the data being translated.
 * @param <CC> The type of the CodeableConcept being translated.
 * @param <C> The type of the configuration used for translation.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class CodeableConceptTranslator<
    D, 
    CC extends IBase,
    C extends FDSConfig & CodeableConceptProcessor<CC>
> implements Translator<D> {

    /* Translator for converting CodeableConcept to string */
    private final TranslationService<C> translationService;
    /* Function to extract the CodeableConcept from the data */
    private final Function<D, CC> extractor;
    /* Predicate to check if the CodeableConcept is present in the data */
    private final Predicate<D> presence;

    @Override
    public CompletableFuture<String> convert(D data) {
        return translationService
                .getConfig()
                .fromCodeableConceptToString(
                        extractor.apply(data)
                );
    }

    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }
    
}
