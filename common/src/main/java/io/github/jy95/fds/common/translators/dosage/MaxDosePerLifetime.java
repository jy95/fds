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
 * Translator for converting a maxDosePerLifetime quantity into a human-readable string format.
 * This class utilizes a translation service to fetch localized messages and a quantity-to-string converter
 * to handle the conversion of the quantity value. It also checks for the presence of the maxDosePerLifetime field
 * in the data before attempting conversion.
 *
 * @param <D> The type of data being translated.
 * @param <Q> The type of quantity being processed, which must implement IBase.
 * @param <C> The type of configuration used for processing quantities, which must extend FDSConfig and implement QuantityProcessor.
 * 
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class MaxDosePerLifetime<D, Q extends IBase, C extends FDSConfig & QuantityProcessor<Q>> implements Translator<D> {

    /* Translation service for handling translation logic */
    private final TranslationService<C> translationService;
    /* Configuration object for processing quantities */
    private final QuantityToString<Q, C> quantityToString;
    /* Predicate to check the presence of the maxDosePerLifetime field in the data */
    private final Predicate<D> presenceChecker;
    /* Function to extract the maxDosePerLifetime quantity from the data */
    private final Function<D, Q> extractor;

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(D data) {
        var quantity = extractor.apply(data);
        var maxDosePerLifetimeMsg = translationService.getMessage("fields.maxDosePerLifetime");

        return quantityToString
            .convert(translationService, quantity)
            .thenApplyAsync((quantityText) -> maxDosePerLifetimeMsg.format(new Object[] { quantityText }));
    }

    /** {@inheritDoc} */
    @Override
    public boolean isPresent(D data) {
        return presenceChecker.test(data);
    }
}
