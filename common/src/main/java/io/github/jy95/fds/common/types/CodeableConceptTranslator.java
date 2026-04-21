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
 * @param <T> The type of the data being translated.
 * @param <U> The type of the CodeableConcept being translated.
 * @param <C> The type of the configuration used for translation.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class CodeableConceptTranslator<
    T, 
    U extends IBase,
    C extends FDSConfig & CodeableConceptProcessor<U>
> implements Translator<T> {

    /* Translator for converting CodeableConcept to string */
    private final TranslationService<C> translationService;
    /* Function to extract the CodeableConcept from the data */
    private final Function<T, U> extractor;
    /* Predicate to check if the CodeableConcept is present in the data */
    private final Predicate<T> presence;

    @Override
    public CompletableFuture<String> convert(T data) {
        return translationService
                .getConfig()
                .fromCodeableConceptToString(
                        extractor.apply(data)
                );
    }

    @Override
    public boolean isPresent(T data) {
        return presence.test(data);
    }
    
}
