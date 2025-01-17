package io.github.jy95.fds.r4.utils;

import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;
import org.hl7.fhir.r4.model.Dosage;

import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Map for R4 translators
 *
 * @author jy95
 */
public class TranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code TranslatorsMapR4}.
     *
     * @param config The configuration object used for translation.
     */
    public TranslatorsMapR4(FDSConfigR4 config, ResourceBundle bundle) {
        super(
                new EnumMap<>(
                        Map.ofEntries(
                                Map.entry(DisplayOrder.TEXT, new TextR4()),
                                Map.entry(DisplayOrder.PATIENT_INSTRUCTION, new PatientInstructionR4()),
                                Map.entry(DisplayOrder.DAY_OF_WEEK, new DayOfWeekR4(config, bundle)),
                                Map.entry(DisplayOrder.TIME_OF_DAY, new TimeOfDayR4(config, bundle)),
                                Map.entry(DisplayOrder.TIMING_CODE, new TimingCodeR4(config)),
                                Map.entry(DisplayOrder.TIMING_EVENT, new TimingEventR4(config, bundle)),
                                Map.entry(DisplayOrder.METHOD, new MethodR4(config)),
                                Map.entry(DisplayOrder.ROUTE, new RouteR4(config)),
                                Map.entry(DisplayOrder.SITE, new SiteR4(config)),
                                Map.entry(DisplayOrder.EXTENSION, new ExtensionR4(config)),
                                Map.entry(DisplayOrder.TIMING_EXTENSION, new TimingExtensionR4(config)),
                                Map.entry(DisplayOrder.TIMING_REPEAT_EXTENSION, new TimingRepeatExtensionR4(config)),
                                Map.entry(DisplayOrder.MODIFIER_EXTENSION,new ModifierExtensionR4(config)),
                                Map.entry(DisplayOrder.TIMING_MODIFIER_EXTENSION, new TimingModifierExtensionR4(config)),
                                Map.entry(DisplayOrder.ADDITIONAL_INSTRUCTION, new AdditionalInstructionR4(config, bundle)),
                                Map.entry(DisplayOrder.AS_NEEDED, new AsNeededR4(config, bundle)),
                                Map.entry(DisplayOrder.BOUNDS_PERIOD, new BoundsPeriodR4(config, bundle)),
                                Map.entry(DisplayOrder.BOUNDS_RANGE, new BoundsRangeR4(config, bundle)),
                                Map.entry(DisplayOrder.DURATION_DURATION_MAX, new DurationDurationMaxR4(config, bundle)),
                                Map.entry(DisplayOrder.FREQUENCY_FREQUENCY_MAX, new FrequencyFrequencyMaxR4(config, bundle)),
                                Map.entry(DisplayOrder.PERIOD_PERIOD_MAX, new PeriodPeriodMaxR4(config, bundle)),
                                Map.entry(
                                        DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
                                        new FrequencyFrequencyMaxPeriodPeriodMaxR4(
                                                new FrequencyFrequencyMaxR4(config, bundle),
                                                new PeriodPeriodMaxR4(config, bundle)
                                        )
                                ),
                                Map.entry(DisplayOrder.COUNT_COUNT_MAX, new CountCountMaxR4(config, bundle)),
                                Map.entry(DisplayOrder.DOSE_QUANTITY, new DoseQuantityR4(config, bundle)),
                                Map.entry(DisplayOrder.DOSE_RANGE, new DoseRangeR4(config, bundle)),
                                Map.entry(DisplayOrder.RATE_QUANTITY, new RateQuantityR4(config, bundle)),
                                Map.entry(DisplayOrder.RATE_RANGE, new RateRangeR4(config, bundle)),
                                Map.entry(DisplayOrder.RATE_RATIO, new RateRatioR4(config, bundle)),
                                Map.entry(DisplayOrder.OFFSET_WHEN, new OffsetWhenR4(bundle)),
                                Map.entry(DisplayOrder.MAX_DOSE_PER_LIFETIME, new MaxDosePerLifetimeR4(config, bundle)),
                                Map.entry(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, new MaxDosePerAdministrationR4(config, bundle)),
                                Map.entry(DisplayOrder.MAX_DOSE_PER_PERIOD, new MaxDosePerPeriodR4(config, bundle)),
                                Map.entry(DisplayOrder.BOUNDS_DURATION, new BoundsDurationR4(config, bundle))
                        )
                )
        );
    }
}
