package io.github.jy95.fds.r4.translators;

import com.ibm.icu.text.MessageFormat;
import io.github.jy95.fds.common.functions.ListToString;
import io.github.jy95.fds.common.translators.TimeOfDay;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.PrimitiveType;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * R4 class for translating "timing.repeat.timeOfDay"
 *
 * @author jy95
 */
public class TimeOfDayR4 implements TimeOfDay<FDSConfigR4, Dosage> {

    // Translations
    /** MessageFormat instance used for "timeOfDay" translation. */
    protected final MessageFormat timeOfDayMsg;

    /**
     * The resource bundle containing localized strings for translation.
     */
    private final ResourceBundle bundle;

    /**
     * Constructor for {@code TimeOfDayR4}.
     *
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public TimeOfDayR4(FDSConfigR4 config, ResourceBundle bundle) {
        this.timeOfDayMsg = getTimeOfDayMsg(bundle, config.getLocale());
        this.bundle = bundle;
    }

    /** {@inheritDoc} */
    @Override
    public List<String> getTimes(Dosage dosage) {
        return dosage
                .getTiming()
                .getRepeat()
                .getTimeOfDay()
                .stream()
                .map(PrimitiveType::getValue)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasRequiredElements(Dosage dosage) {
        var timing = dosage.getTiming();
        return timing.hasRepeat() && timing.getRepeat().hasTimeOfDay();
    }

    /** {@inheritDoc} */
    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var times = getTimes(dosage);
            var timeOfDays = times.stream().map(this::formatString).collect(Collectors.toList());
            var timeOfDaysAsString = ListToString.convert(bundle, timeOfDays);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", timeOfDays.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }
}
