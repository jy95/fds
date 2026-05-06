package io.github.jy95.fds.r4.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.common.types.ExtensionTranslator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.functions.*;
import io.github.jy95.fds.r4.translators.*;
import io.github.jy95.fds.common.translators.timing.repeat.*;

/**
 * Map for R4 Timing repeat translators
 *
 * @author jy95
 */
public class TimingRepeatTranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, TimingRepeatComponent> {

    /**
     * Constructor for {@code TimingRepeatTranslatorsMapR4}.
     *
     * @param translationService The translation service used for translation.
     */
    public TimingRepeatTranslatorsMapR4(TranslationService<FDSConfigR4> translationService) {
        super(translationService);
    }

    /** {@inheritDoc} */
    @Override
    protected Map<DisplayOrder, Supplier<Translator<TimingRepeatComponent>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<TimingRepeatComponent>>> suppliers = new EnumMap<>(DisplayOrder.class);

        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new ExtensionTranslator<>(
                translationService,
                TimingRepeatComponent::getExtension,
                TimingRepeatComponent::hasExtension
        ));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDuration<>(
                translationService,
                QuantityToStringR4.INSTANCE,
                TimingRepeatComponent::hasBoundsDuration,
                TimingRepeatComponent::getBoundsDuration
        ));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRange<>(
                translationService,
                RangeToStringR4.INSTANCE,
                TimingRepeatComponent::hasBoundsRange,
                TimingRepeatComponent::getBoundsRange
        ));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR4(translationService));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMax<>(
                translationService,
                TimingRepeatComponent::hasCount,
                TimingRepeatComponent::hasCountMax,
                TimingRepeatComponent::getCount,
                TimingRepeatComponent::getCountMax
        ));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> DurationDurationMax.<TimingRepeatComponent>builder()
                .translationService(translationService)
                .hasDurationUnit(t -> t.hasDurationUnit())
                .hasDuration(t -> t.hasDuration())
                .hasDurationMax(t -> t.hasDurationMax())
                .getDurationUnit(t -> t.getDurationUnit().toCode())
                .getDuration(t -> t.getDuration())
                .getDurationMax(t -> t.getDurationMax())
                .build()
        );
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMax<>(
                translationService,
                TimingRepeatComponent::hasFrequency,
                TimingRepeatComponent::hasFrequencyMax,
                TimingRepeatComponent::getFrequency,
                TimingRepeatComponent::getFrequencyMax
        ));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR4(translationService));
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeek<>(
                translationService,
                TimingRepeatComponent::getDayOfWeek,
                TimingRepeatComponent::hasDayOfWeek
        ));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDay<>(
                translationService,
                TimingRepeatComponent::getTimeOfDay,
                TimingRepeatComponent::hasTimeOfDay
        ));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhen<>(
                translationService,
                TimingRepeatComponent::getWhen,
                TimingRepeatComponent::hasWhen,
                TimingRepeatComponent::getOffset,
                TimingRepeatComponent::hasOffset
        ));

        // Composite translator with dependencies
        suppliers.put(
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
                () -> new FrequencyFrequencyMaxPeriodPeriodMax<>(
                        suppliers.get(DisplayOrder.FREQUENCY_FREQUENCY_MAX).get(),
                        suppliers.get(DisplayOrder.PERIOD_PERIOD_MAX).get()
                )
        );

        return suppliers;
    }

}
