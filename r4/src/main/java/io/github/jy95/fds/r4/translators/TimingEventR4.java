package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractTimingEvent;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Dosage;

import java.util.List;

/**
 * R4 class for translating "timing.event"
 *
 * @author jy95
 */
public class TimingEventR4 extends AbstractTimingEvent<FDSConfigR4, Dosage> {

    private final FormatDateTimesR4 formatDateTimesR4;

    /**
     * Constructor for {@code TimingEventR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TimingEventR4(FDSConfigR4 config) {
        super(config);
        formatDateTimesR4 = new FormatDateTimesR4();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasEvent();
    }

    /** {@inheritDoc} */
    @Override
    protected List<String> getEvents(Dosage dosage) {
        DateTimeType[] events = dosage.getTiming().getEvent().toArray(DateTimeType[]::new);
        return formatDateTimesR4.convert(this.getConfig().getLocale(), events);
    }
}
