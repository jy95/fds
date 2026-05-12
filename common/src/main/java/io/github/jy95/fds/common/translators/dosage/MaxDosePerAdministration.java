package io.github.jy95.fds.common.translators.dosage;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import org.hl7.fhir.instance.model.api.IBase;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.QuantityToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.operations.QuantityProcessor;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Translator for maximum dose per administration.
 *
 * @param <D> The type of the data object containing the dosage information.
 * @param <Q> The type of the quantity representing the maximum dose.
 * @param <C> The type of the configuration used for translation.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class MaxDosePerAdministration<
D, 
Q extends IBase, 
C extends FDSConfig & QuantityProcessor<Q>>
implements Translator<D> {
    
    /** Translation service */
    private final TranslationService<C> translationService;
    /** Presence checker */
    private final Predicate<D> presenceChecker;
    /** Quantity extractor */
    private final Function<D, Q> extractor;
    /** Quantity to string converter */
    private final QuantityToString<Q, C> quantityToString;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D data) {
        var quantity = extractor.apply(data);
        var maxDosePerAdministrationMsg = translationService.getMessage("fields.maxDosePerAdministration");

        return quantityToString
                .convert(translationService, quantity)
                .thenApplyAsync(
                        (quantityText) -> maxDosePerAdministrationMsg.format(new Object[] { quantityText })
                );
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D data) {
        return presenceChecker.test(data);
    }
}
