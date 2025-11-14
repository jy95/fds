package io.github.jy95.fds.r5.utils;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.translators.*;
import org.hl7.fhir.r5.model.Dosage;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Map for R5 translators
 *
 * @author jy95
 */
public class TranslatorsMapR5 extends AbstractTranslatorsMap<FDSConfigR5, Dosage> {

    /**
     * Constructor for {@code TranslatorsMapR5}.
     *
     * @param translationService The translation service used for translation.
     */
    public TranslatorsMapR5(TranslationService<FDSConfigR5> translationService) {
        super(translationService);
    }


    /** {@inheritDoc} */
    @Override
    protected Map<DisplayOrder, Supplier<Translator<Dosage>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<Dosage>>> suppliers = new EnumMap<>(DisplayOrder.class);

        // All display order supported by R5 are initialized here
        suppliers.put(DisplayOrder.TEXT, TextR5::new);
        suppliers.put(DisplayOrder.PATIENT_INSTRUCTION, PatientInstructionR5::new);
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeekR5(translationService));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDayR5(translationService));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR5(translationService));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR5(translationService));
        suppliers.put(DisplayOrder.METHOD, () -> new MethodR5(translationService));
        suppliers.put(DisplayOrder.ROUTE, () -> new RouteR5(translationService));
        suppliers.put(DisplayOrder.SITE, () -> new SiteR5(translationService));
        suppliers.put(DisplayOrder.EXTENSION, () -> new ExtensionR5(translationService));
        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR5(translationService));
        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new TimingRepeatExtensionR5(translationService));
        suppliers.put(DisplayOrder.MODIFIER_EXTENSION, () -> new ModifierExtensionR5(translationService));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR5(translationService));
        suppliers.put(DisplayOrder.ADDITIONAL_INSTRUCTION, () -> new AdditionalInstructionR5(translationService));
        suppliers.put(DisplayOrder.AS_NEEDED, () -> new AsNeededR5(translationService));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR5(translationService));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRangeR5(translationService));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> new DurationDurationMaxR5(translationService));
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMaxR5(translationService));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR5(translationService));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMaxR5(translationService));
        suppliers.put(DisplayOrder.DOSE_QUANTITY, () -> new DoseQuantityR5(translationService));
        suppliers.put(DisplayOrder.DOSE_RANGE, () -> new DoseRangeR5(translationService));
        suppliers.put(DisplayOrder.RATE_QUANTITY, () -> new RateQuantityR5(translationService));
        suppliers.put(DisplayOrder.RATE_RANGE, () -> new RateRangeR5(translationService));
        suppliers.put(DisplayOrder.RATE_RATIO, () -> new RateRatioR5(translationService));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhenR5(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_LIFETIME, () -> new MaxDosePerLifetimeR5(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, () -> new MaxDosePerAdministrationR5(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_PERIOD, () -> new MaxDosePerPeriodR5(translationService));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDurationR5(translationService));

        // Composite translator with dependencies
        suppliers.put(
                DisplayOrder.FREQUENCY_FREQUENCY_MAX_PERIOD_PERIOD_MAX,
                () -> new FrequencyFrequencyMaxPeriodPeriodMaxR5(
                        suppliers.get(DisplayOrder.FREQUENCY_FREQUENCY_MAX).get(),
                        suppliers.get(DisplayOrder.PERIOD_PERIOD_MAX).get()
                )
        );

        return suppliers;
    }
}
