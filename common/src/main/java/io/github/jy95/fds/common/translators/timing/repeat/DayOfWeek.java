package io.github.jy95.fds.common.translators.timing.repeat;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.jy95.fds.common.config.FDSConfig;
import io.github.jy95.fds.common.functions.DayOfWeekFormatter;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.Translator;
import lombok.RequiredArgsConstructor;

import org.hl7.fhir.instance.model.api.IPrimitiveType;
import org.hl7.fhir.instance.model.api.IBaseEnumeration;

/**
 * Translator for the dayOfWeek field in Timing repeat components.
 *
 * @param <D> The type of data being translated.
 * @param <V> The type of the dayOfWeek values, which must be an enumeration.
 * @param <C> The type of the configuration used for translation.
 * @author jy95
 * @since 2.1.9
 */
@RequiredArgsConstructor
public class DayOfWeek<
D,
V extends IBaseEnumeration<?>,
C extends FDSConfig> implements Translator<D> {

    /* The translation service used for translation. */
    private final TranslationService<C> translationService;
    /* Function to extract the list of dayOfWeek values from the data. */
    private final Function<D, List<V>> extractor;
    /* Predicate to check the presence of dayOfWeek values in the data. */
    private final Predicate<D> presence;

    @Override
    public CompletableFuture<String> convert(D data) {
        var locale = translationService.getConfig().getLocale();

        return CompletableFuture.supplyAsync(() -> {

            var dayOfWeekMsg = translationService.getMessage("fields.dayOfWeek");

            // Internal mapping from FHIR Enumeration to String codes
            var dayOfWeeksCodes = extractor
                .apply(data)
                .stream()
                .map(IPrimitiveType::getValue)
                .map(String::valueOf)
                .map(code -> DayOfWeekFormatter.codeToLongText(code.toLowerCase(), locale))
                .toList();

            var dayOfWeeksAsString = ListToString.convert(translationService, dayOfWeeksCodes);

            Map<String, Object> messageArguments = Map.of(
                    "dayCondition", dayOfWeeksCodes.size(),
                    "day", dayOfWeeksAsString
            );

            return dayOfWeekMsg.format(messageArguments);
        });
    }

    @Override
    public boolean isPresent(D data) {
        return presence.test(data);
    }
}