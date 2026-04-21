package io.github.jy95.fds.common.types;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;

/**
 * Translator for simple string fields extracted from a FHIR element.
 * The supplied extractor returns the string value, and the supplied predicate
 * determines whether the value is present.
 *
 * @param <D> the type of fhir element containing field to be translated
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class SimpleStringTranslator<D> implements Translator<D> {

    /* The function to extract the string value from the data. */
    private final Function<D, String> extractor;
    /* The predicate to check if the string value is present in the data. */
    private final Predicate<D> presence;

    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.completedFuture(extractor.apply(data));
    }

    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }
    
}
