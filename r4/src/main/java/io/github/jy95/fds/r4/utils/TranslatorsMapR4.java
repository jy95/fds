package io.github.jy95.fds.r4.utils;

import io.github.jy95.fds.common.functions.TranslationService;
import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;
import org.hl7.fhir.r4.model.Dosage;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Map for R4 translators
 *
 * @author jy95
 */
public class TranslatorsMapR4 extends AbstractTranslatorsMap<FDSConfigR4, Dosage> {

    /**
     * Constructor for {@code TranslatorsMapR4}.
     *
     * @param translationService The translation service used for translation.
     */
    public TranslatorsMapR4(TranslationService<FDSConfigR4> translationService) {
        super(translationService);
    }

    /** {@inheritDoc} */
    @Override
    protected Map<DisplayOrder, Supplier<Translator<FDSConfigR4, Dosage>>> createTranslatorsSuppliers() {
        EnumMap<DisplayOrder, Supplier<Translator<FDSConfigR4, Dosage>>> suppliers = new EnumMap<>(DisplayOrder.class);

        // All display order supported by R4 are initialized here
        suppliers.put(DisplayOrder.TEXT, TextR4::new);
        suppliers.put(DisplayOrder.PATIENT_INSTRUCTION, PatientInstructionR4::new);
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeekR4(translationService));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDayR4(translationService));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR4(translationService));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR4(translationService));
        suppliers.put(DisplayOrder.METHOD, () -> new MethodR4(translationService));
        suppliers.put(DisplayOrder.ROUTE, () -> new RouteR4(translationService));
        suppliers.put(DisplayOrder.SITE, () -> new SiteR4(translationService));
        suppliers.put(DisplayOrder.EXTENSION, () -> new ExtensionR4(translationService));
        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR4(translationService));
        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new TimingRepeatExtensionR4(translationService));
        suppliers.put(DisplayOrder.MODIFIER_EXTENSION, () -> new ModifierExtensionR4(translationService));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR4(translationService));
        suppliers.put(DisplayOrder.ADDITIONAL_INSTRUCTION, () -> new AdditionalInstructionR4(translationService));
        suppliers.put(DisplayOrder.AS_NEEDED, () -> new AsNeededR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRangeR4(translationService));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> new DurationDurationMaxR4(translationService));
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMaxR4(translationService));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR4(translationService));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMaxR4(translationService));
        suppliers.put(DisplayOrder.DOSE_QUANTITY, () -> new DoseQuantityR4(translationService));
        suppliers.put(DisplayOrder.DOSE_RANGE, () -> new DoseRangeR4(translationService));
        suppliers.put(DisplayOrder.RATE_QUANTITY, () -> new RateQuantityR4(translationService));
        suppliers.put(DisplayOrder.RATE_RANGE, () -> new RateRangeR4(translationService));
        suppliers.put(DisplayOrder.RATE_RATIO, () -> new RateRatioR4(translationService));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhenR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_LIFETIME, () -> new MaxDosePerLifetimeR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, () -> new MaxDosePerAdministrationR4(translationService));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_PERIOD, () -> new MaxDosePerPeriodR4(translationService));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDurationR4(translationService));

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
