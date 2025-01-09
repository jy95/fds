package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.functions.ListToString;
import jy95.fhir.common.types.AbstractTranslatorTiming;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractTimeOfDay<C extends FDSConfig, D> extends AbstractTranslatorTiming<C, D> {
    
    // Translations
    protected final MessageFormat timeOfDayMsg;

    public AbstractTimeOfDay(C config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg = bundle.getString("fields.timeOfDay");
        timeOfDayMsg = new MessageFormat(msg, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var bundle = this.getResources();
            var times = getTimes(dosage);
            var timeOfDays = times.stream().map(this::formatString).toList();
            var timeOfDaysAsString = ListToString.convert(bundle, timeOfDays);

            Map<String, Object> messageArguments = Map.of(
                    "timeOfDay", timeOfDaysAsString,
                    "count", timeOfDays.size()
            );

            return timeOfDayMsg.format(messageArguments);
        });
    }

    protected abstract List<String> getTimes(D dosage);

    /**
     * Time during the day, in the format hh:mm:ss (a subset of [ISO8601] icon).
     * There is no date specified. Seconds must be provided due to schema type constraints
     * but may be zero-filled and may be ignored at receiver discretion.
     */
    protected String formatString(String time) {
        String[] parts = time.split(":");

        if (parts.length > 2 && parts[2].equals("00")) {
            parts = new String[] { parts[0], parts[1] };
        }

        return String.join(":", parts);
    }
}
