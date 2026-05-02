package io.github.jy95.fds.common.translators.timing.repeat;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

/**
 * Translator for "timing.repeat.count" / "timing.repeat.countMax".
 *
 * @param <D> The type of the data to translate.
 * @param <C> The type of the configuration.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class CountCountMax<D, C extends FDSConfig> implements Translator<D> {
    
    /* The translation service used for translation. */
    private final TranslationService<C> translationService;
    /* Predicate to check the presence of "timing.repeat.count". */
    private final Predicate<D> hasCount;  
    /* Predicate to check the presence of "timing.repeat.countMax". */
    private final Predicate<D> hasCountMax;  
    /* Function to extract "timing.repeat.count" from the data object. */
    private final Function<D, Integer> getCount;  
    /* Function to extract "timing.repeat.countMax" from the data object. */
    private final Function<D, Integer> getCountMax;

    @Override
    public boolean isPresent(D data) {
        return hasCount.test(data) || hasCountMax.test(data);
    }

    @Override
    public CompletableFuture<String> convert(D data) {
        return CompletableFuture.supplyAsync(() -> {

            // Rule: If there's a countMax, there must be a count
            if (hasCountMax.test(data)) {
                var arguments = Map.of(
                        "minCount", getCount.apply(data),
                        "maxCount", getCountMax.apply(data)
                );
                var countMaxMsg = translationService.getMessage("fields.countMax");
                return countMaxMsg.format(arguments);
            }

            var countMsg = translationService.getMessage("fields.count");
            return countMsg.format(new Object[]{
                    getCount.apply(data)
            });

        });
    }

}
