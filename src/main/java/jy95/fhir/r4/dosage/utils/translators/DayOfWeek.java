package jy95.fhir.r4.dosage.utils.translators;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class DayOfWeek extends AbstractTranslator {

    public DayOfWeek(FDUConfig config){
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = dosage.getTiming().getRepeat().getDayOfWeek();
            var bundle = this.getResources();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> bundle.getString(day.getCode()))
                    .toList();

            var dayOfWeeksAsString = ListToString.convert(bundle, dayOfWeeksCodes);
            var msg = bundle.getString("fields.dayOfWeek");

            return MessageFormat.format(msg, dayOfWeeks.size(), dayOfWeeksAsString);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasDayOfWeek();
    }

}
