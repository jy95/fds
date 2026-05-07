package io.github.jy95.fds.r4.functions;

import org.hl7.fhir.r4.model.DateTimeType;
import org.hl7.fhir.r4.model.Period;

import io.github.jy95.fds.common.functions.PeriodToString;
import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.r4.config.FDSConfigR4;

public enum PeriodToStringR4 implements PeriodToString<Period, DateTimeType, FDSConfigR4> {
    
    // Singleton instance
    INSTANCE;

    /** {@inheritDoc} */
    @Override
    public boolean hasStart(Period period) {
        return period.hasStart();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasEnd(Period period) {
        return period.hasEnd();
    }

    /** {@inheritDoc} */
    @Override
    public DateTimeType getStart(Period period) {
        return period.getStartElement();
    }

    /** {@inheritDoc} */
    @Override
    public DateTimeType getEnd(Period period) {
        return period.getEndElement();
    }

    /** {@inheritDoc} */
    @Override
    public String formatDateTime(TranslationService<FDSConfigR4> translationService, DateTimeType date) {
        return translationService.dateTimeToHumanDisplay(
            date.getValue(), 
            date.getTimeZone(), 
            date.getPrecision()
        );
    }

}
