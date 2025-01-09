package jy95.fhir.r4.translators;

import jy95.fhir.common.translators.AbstractBoundsPeriod;
import jy95.fhir.r4.config.FDSConfigR4;
import jy95.fhir.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class BoundsPeriodR4 extends AbstractBoundsPeriod<FDSConfigR4, Dosage> {

    private final FormatDateTimesR4 formatDateTimesR4;

    public BoundsPeriodR4(FDSConfigR4 config) {
        super(config);
        formatDateTimesR4 = new FormatDateTimesR4();
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
            var hasStart = boundPeriods.hasStart();
            var hasEnd = boundPeriods.hasEnd();
            var locale = this.getConfig().getLocale();

            // Prepare date values using FormatDateTimes.convert()
            String startDate = hasStart ? formatDateTimesR4.convert(locale, boundPeriods.getStartElement()) : "";
            String endDate = hasEnd ? formatDateTimesR4.convert(locale, boundPeriods.getEndElement()) : "";

            // Choose the correct condition based on the presence of start and end dates
            String condition = hasStart && hasEnd ? "0" : (hasStart ? "1" : "other");

            // Create a map of named arguments
            Map<String, Object> arguments = Map.of(
                    "startDate", startDate,
                    "endDate", endDate,
                    "condition", condition
            );

            // Format the message with the named arguments
            return boundsPeriodMsg.format(arguments);
        });
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }
}
