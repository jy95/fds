package jy95.fhir.r4.dosage.utils.translators;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;
import org.hl7.fhir.r4.model.TimeType;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;

public class TimeOfDay extends AbstractTranslator {

    public TimeOfDay(FDUConfig config){
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var timeOfDay = dosage.getTiming().getRepeat().getTimeOfDay();
            var bundle = this.getResources();
            var timeOfDays = timeOfDay.stream()
                    .map(this::formatString)
                    .toList();

            var timeOfDaysAsString = ListToString.convert(bundle, timeOfDays);
            var message = bundle.getString("fields.timeOfDay");

            return MessageFormat.format(message, timeOfDaysAsString, timeOfDays.size());
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return !dosage.getTiming().getRepeat().getTimeOfDay().isEmpty();
    }

    /**
     * Time during the day, in the format hh:mm:ss (a subset of [ISO8601] icon).
     * There is no date specified. Seconds must be provided due to schema type constraints
     * but may be zero-filled and may be ignored at receiver discretion.
     */
    private String formatString(TimeType time) {
        String[] parts = time.getValue().split(":");

        if (parts.length > 2 && parts[2].equals("00")) {
            parts = new String[] { parts[0], parts[1] };
        }

        return String.join(":", parts);
    }
}
