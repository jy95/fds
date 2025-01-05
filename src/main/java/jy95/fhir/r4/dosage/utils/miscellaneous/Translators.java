package jy95.fhir.r4.dosage.utils.miscellaneous;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import jy95.fhir.r4.dosage.utils.translators.*;
import lombok.Getter;

import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

@Getter
public class Translators {
    private final Map<DisplayOrder, AbstractTranslator> translatorMap;
    private final MultiResourceBundleControl bundleControl;
    private final ResourceBundle resources;

    public Translators(FDUConfig config) {
        this.translatorMap = new EnumMap<>(
                Map.ofEntries(
                        Map.entry(DisplayOrder.TEXT, new Text(config)),
                        Map.entry(DisplayOrder.PATIENT_INSTRUCTION, new PatientInstruction(config)),
                        Map.entry(DisplayOrder.DAY_OF_WEEK, new DayOfWeek(config)),
                        Map.entry(DisplayOrder.TIME_OF_DAY, new TimeOfDay(config)),
                        Map.entry(DisplayOrder.TIMING_CODE, new TimingCode(config)),
                        Map.entry(DisplayOrder.TIMING_EVENT, new TimingEvent(config)),
                        Map.entry(DisplayOrder.METHOD, new Method(config)),
                        Map.entry(DisplayOrder.ROUTE, new Route(config)),
                        Map.entry(DisplayOrder.SITE, new Site(config)),
                        Map.entry(DisplayOrder.EXTENSION, new Extension(config)),
                        Map.entry(DisplayOrder.TIMING_EXTENSION, new TimingExtension(config)),
                        Map.entry(DisplayOrder.TIMING_REPEAT_EXTENSION, new TimingRepeatExtension(config)),
                        Map.entry(DisplayOrder.MODIFIER_EXTENSION,new ModifierExtension(config)),
                        Map.entry(DisplayOrder.TIMING_MODIFIER_EXTENSION, new TimingModifierExtension(config)),
                        Map.entry(DisplayOrder.ADDITIONAL_INSTRUCTION, new AdditionalInstruction(config)),
                        Map.entry(DisplayOrder.AS_NEEDED, new AsNeeded(config)),
                        Map.entry(DisplayOrder.BOUNDS_PERIOD, new BoundsPeriod(config)),
                        Map.entry(DisplayOrder.BOUNDS_RANGE, new BoundsRange(config)),
                        Map.entry(DisplayOrder.DURATION_DURATION_MAX, new DurationDurationMax(config)),
                        Map.entry(DisplayOrder.FREQUENCY_FREQUENCY_MAX, new FrequencyFrequencyMax(config)),
                        Map.entry(DisplayOrder.PERIOD_PERIOD_MAX, new PeriodPeriodMax(config)),
                        Map.entry(
                                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
                                new FrequencyFrequencyMaxPeriodPeriodMax(config)
                        ),
                        Map.entry(DisplayOrder.COUNT_COUNT_MAX, new CountCountMax(config)),
                        Map.entry(DisplayOrder.DOSE_QUANTITY, new DoseQuantity(config)),
                        Map.entry(DisplayOrder.DOSE_RANGE, new DoseRange(config)),
                        Map.entry(DisplayOrder.RATE_QUANTITY, new RateQuantity(config)),
                        Map.entry(DisplayOrder.RATE_RANGE, new RateRange(config)),
                        Map.entry(DisplayOrder.RATE_RATIO, new RateRatio(config)),
                        Map.entry(DisplayOrder.OFFSET_WHEN, new OffsetWhen(config)),
                        Map.entry(DisplayOrder.MAX_DOSE_PER_LIFETIME, new MaxDosePerLifetime(config)),
                        Map.entry(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, new MaxDosePerAdministration(config)),
                        Map.entry(DisplayOrder.MAX_DOSE_PER_PERIOD, new MaxDosePerPeriod(config))
                )
        );
        this.bundleControl = new MultiResourceBundleControl(
                "translations",
                "common",
                "daysOfWeek",
                "eventTiming",
                "quantityComparator",
                "unitsOfTime"
        );
        this.resources = ResourceBundle.getBundle(
                this.bundleControl.getBaseName(),
                config.getLocale(),
                this.bundleControl
        );
    }

    public AbstractTranslator getTranslator(DisplayOrder displayOrder) {
        return this.translatorMap.get(displayOrder);
    }
}
