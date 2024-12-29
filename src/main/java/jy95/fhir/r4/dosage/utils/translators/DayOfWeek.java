package jy95.fhir.r4.dosage.utils.translators;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import lombok.Getter;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;

public class DayOfWeek extends AbstractTranslator {

    private final ResourceBundle bundle;
    @Getter
    private final DisplayOrder field;

    public DayOfWeek(ResourceBundle bundle){
        this.bundle = bundle;
        this.field = DisplayOrder.DAY_OF_WEEK;
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = dosage.getTiming().getRepeat().getDayOfWeek();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> bundle.getString(day.getCode()))
                    .toList();

            var dayOfWeeksAsString = ListToString.convert(this.bundle, dayOfWeeksCodes);
            var msg = bundle.getString("fields.dayOfWeek");

            return MessageFormat.format(msg, dayOfWeeks.size(), dayOfWeeksAsString);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return !dosage.getTiming().getRepeat().getDayOfWeek().isEmpty();
    }

}
