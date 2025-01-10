package io.github.jy95.r4.translators;

import io.github.jy95.common.translators.AbstractTimingEvent;
import io.github.jy95.r4.config.FDSConfigR4;
import io.github.jy95.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;

public class TimingEventR4 extends AbstractTimingEvent<FDSConfigR4, Dosage> {

    private final FormatDateTimesR4 formatDateTimesR4;

    public TimingEventR4(FDSConfigR4 config) {
        super(config);
        formatDateTimesR4 = new FormatDateTimesR4();
    }

    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasEvent();
    }

    @Override
    protected List<String> getEvents(Dosage dosage) {
        DateTimeType[] events = dosage.getTiming().getEvent().toArray(DateTimeType[]::new);
        return formatDateTimesR4.convert(this.getConfig().getLocale(), events);
    }
}
