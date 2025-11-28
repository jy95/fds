package io.github.jy95.fds.r4.utils.maps;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import org.hl7.fhir.r4.model.Timing.TimingRepeatComponent;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;

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

        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new TimingRepeatExtensionR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDurationR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRangeR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR4(translationService));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMaxR4(translationService));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> new DurationDurationMaxR4(translationService));
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMaxR4(translationService));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR4(translationService));
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeekR4(translationService));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDayR4(translationService));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhenR4(translationService));

        // Composite translator with dependencies
        suppliers.put(
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
                () -> new FrequencyFrequencyMaxPeriodPeriodMaxR4(
                        suppliers.get(DisplayOrder.FREQUENCY_FREQUENCY_MAX).get(),
                        suppliers.get(DisplayOrder.PERIOD_PERIOD_MAX).get()
                )
        );

        return suppliers;
    }

}
