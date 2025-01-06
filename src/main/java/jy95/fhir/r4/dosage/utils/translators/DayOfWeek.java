package jy95.fhir.r4.dosage.utils.translators;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class DayOfWeek extends AbstractTranslator {

    // Translations
    private final MessageFormat dayOfWeekMsg;

    public DayOfWeek(FDUConfig config){
        super(config);
        var msg = getResources().getString("fields.dayOfWeek");
        dayOfWeekMsg = new MessageFormat(msg, getConfig().getLocale());
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = dosage.getTiming().getRepeat().getDayOfWeek();
            var bundle = this.getResources();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> {

                        String dayCode = day.getCode().toLowerCase(); // Get the lowercase day code
                        String dayTranslation = bundle.getString("day." + dayCode);

                        // Use ICU's MessageFormat to handle the translation with choice formatting
                        MessageFormat messageFormat = new MessageFormat(dayTranslation, this.getConfig().getLocale());
                        Map<String, Object> dayArguments = Map.of(
                                "dayType", "long"
                        );

                        return messageFormat.format(dayArguments);
                    })
                    .toList();

            var dayOfWeeksAsString = ListToString.convert(bundle, dayOfWeeksCodes);

            Map<String, Object> messageArguments = Map.of(
                    "dayCondition", dayOfWeeks.size(),
                    "day", dayOfWeeksAsString
            );

            // Use ICU MessageFormat for plural and select formatting
            return dayOfWeekMsg.format(messageArguments);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasDayOfWeek();
    }

}
