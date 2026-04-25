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

@RequiredArgsConstructor
public class DayOfWeek<D, C extends FDSConfig> implements Translator<D> {

    /* The translation service used for translation. */
    private final TranslationService<C> translationService;
    /* Function to extract the list of dayOfWeek values from the data. */
    private final Function<D, ? extends List<? extends IPrimitiveType<String>>> extractor;
    /* Predicate to check the presence of dayOfWeek values in the data. */
    private final Predicate<D> presence;

    @Override
    public CompletableFuture<String> convert(D data) {
        var locale = translationService.getConfig().getLocale();

        return CompletableFuture.supplyAsync(() -> {

            var dayOfWeekMsg = translationService.getMessage("fields.dayOfWeek");

            // Internal mapping from FHIR Enumeration to String code
            var dayOfWeeksCodes = extractor.apply(data)
                    .stream()
                    .map(IPrimitiveType::getValue) // Gets "mon", "tue", etc.
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