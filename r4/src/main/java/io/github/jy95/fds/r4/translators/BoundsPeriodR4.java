package io.github.jy95.fds.r4.translators;

import io.github.jy95.fds.common.translators.AbstractBoundsPeriod;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.FormatDateTimesR4;
import org.hl7.fhir.r4.model.Dosage;

/**
 * R4 class for translating "timing.repeat.boundsPeriod"
 *
 * @author jy95
 */
public class BoundsPeriodR4 extends AbstractBoundsPeriod<FDSConfigR4, Dosage> {

    private final FormatDateTimesR4 formatDateTimesR4;

    /**
     * Constructor for {@code BoundsPeriodR4}.
     *
     * @param config The configuration object used for translation.
     */
    public BoundsPeriodR4(FDSConfigR4 config) {
        super(config);
        formatDateTimesR4 = new FormatDateTimesR4();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasRequiredElements(Dosage dosage) {
        return dosage.getTiming().hasRepeat() && dosage.getTiming().getRepeat().hasBoundsPeriod();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasTiming(Dosage dosage) {
        return dosage.hasTiming();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasStartPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasStart();
    }

    /** {@inheritDoc} */
    @Override
    protected boolean hasEndPeriod(Dosage dosage) {
        return dosage.getTiming().getRepeat().getBoundsPeriod().hasEnd();
    }

    /** {@inheritDoc} */
    @Override
    protected String formatStartPeriod(Dosage dosage) {
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        var locale = this.getConfig().getLocale();
        return formatDateTimesR4.convert(locale, boundPeriods.getStartElement());
    }

    /** {@inheritDoc} */
    @Override
    protected String formatEndPeriod(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var boundPeriods = dosage.getTiming().getRepeat().getBoundsPeriod();
        return formatDateTimesR4.convert(locale, boundPeriods.getEndElement());
    }
}
