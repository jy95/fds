package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractDayOfWeek;
import io.github.jy95.r4.config.FDSConfigR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.concurrent.CompletableFuture;

public class DayOfWeekR4 extends AbstractDayOfWeek<FDSConfigR4, Dosage> {

    public DayOfWeekR4(FDSConfigR4 config) {
        super(config);
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasDayOfWeek();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var dayOfWeeks = dosage.getTiming().getRepeat().getDayOfWeek();
            var dayOfWeeksCodes = dayOfWeeks
                    .stream()
                    .map(day -> {
                        String dayCode = day.getCode().toLowerCase(); // Get the lowercase day code
                        return dayToText(dayCode);
                    })
                    .toList();

            return daysToText(dayOfWeeksCodes);
        });
    }
}
