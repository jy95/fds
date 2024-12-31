package jy95.fhir.r4.dosage.utils.translators;

import java.text.MessageFormat;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.FormatDateTimes;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class BoundsPeriod extends AbstractTranslator {

    public BoundsPeriod(FDUConfig config) {
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
            var hasStart = boundPeriods.hasStart();
            var hasEnd = boundPeriods.hasEnd();
            var bundle = this.getResources();
            var locale = this.getConfig().getLocale();
            var msg = bundle.getString("fields.boundsPeriod");

            // Both start and end present
            if (hasStart && hasEnd) {
                return MessageFormat.format(
                        msg,
                        0,
                        FormatDateTimes.convert(locale, boundPeriods.getStartElement()),
                        FormatDateTimes.convert(locale, boundPeriods.getEndElement())
                );
            }

            // Only start present
            if (hasStart) {
                return MessageFormat.format(
                        msg,
                        1,
                        FormatDateTimes.convert(locale, boundPeriods.getStartElement())
                );
            }

            // Only end present
            return MessageFormat.format(
                    msg,
                    2,
                    FormatDateTimes.convert(locale, boundPeriods.getEndElement())
            );

        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }
}
