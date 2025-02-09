package io.github.jy95.fds.r5.utils;

import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r5.config.FDSConfigR5;
import io.github.jy95.fds.r5.translators.*;
import org.hl7.fhir.r5.model.Dosage;

import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;
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
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public TranslatorsMapR5(FDSConfigR5 config, ResourceBundle bundle) {
        super(createTranslatorsSuppliers(config, bundle));
    }


    /**
     * Creates a map of {@link DisplayOrder} to {@link Supplier} instances that lazily initialize the corresponding
     * {@link Translator} objects for the R5 configuration.
     * <p>
     * This method ensures that translators are only created when needed, improving efficiency and reducing
     * unnecessary resource usage.It supports both simple translators and composite translators that depend
     * on other translators.
     * </p>
     *
     * @param config the {@link FDSConfigR5} configuration object used by the translators
     * @param bundle the {@link ResourceBundle} providing localized resources for the translators
     * @return a {@link Map} where the keys are {@link DisplayOrder} values and the values are {@link Supplier}
     *         instances that produce the corresponding {@link Translator} objects
     */
    private static Map<DisplayOrder, Supplier<Translator<FDSConfigR5, Dosage>>> createTranslatorsSuppliers(
            FDSConfigR5 config, ResourceBundle bundle) {
        EnumMap<DisplayOrder, Supplier<Translator<FDSConfigR5, Dosage>>> suppliers = new EnumMap<>(DisplayOrder.class);

        // All display order supported by R5 are initialized here
        suppliers.put(DisplayOrder.TEXT, TextR5::new);
        suppliers.put(DisplayOrder.PATIENT_INSTRUCTION, PatientInstructionR5::new);
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeekR5(config, bundle));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDayR5(config, bundle));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR5(config));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR5(config, bundle));
        suppliers.put(DisplayOrder.METHOD, () -> new MethodR5(config));
        suppliers.put(DisplayOrder.ROUTE, () -> new RouteR5(config));
        suppliers.put(DisplayOrder.SITE, () -> new SiteR5(config));
        suppliers.put(DisplayOrder.EXTENSION, () -> new ExtensionR5(config));
        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR5(config));
        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new TimingRepeatExtensionR5(config));
        suppliers.put(DisplayOrder.MODIFIER_EXTENSION, () -> new ModifierExtensionR5(config));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR5(config));
        suppliers.put(DisplayOrder.ADDITIONAL_INSTRUCTION, () -> new AdditionalInstructionR5(config, bundle));
        suppliers.put(DisplayOrder.AS_NEEDED, () -> new AsNeededR5(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR5(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRangeR5(config, bundle));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> new DurationDurationMaxR5(config, bundle));
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMaxR5(config, bundle));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR5(config, bundle));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMaxR5(config, bundle));
        suppliers.put(DisplayOrder.DOSE_QUANTITY, () -> new DoseQuantityR5(config, bundle));
        suppliers.put(DisplayOrder.DOSE_RANGE, () -> new DoseRangeR5(config, bundle));
        suppliers.put(DisplayOrder.RATE_QUANTITY, () -> new RateQuantityR5(config, bundle));
        suppliers.put(DisplayOrder.RATE_RANGE, () -> new RateRangeR5(config, bundle));
        suppliers.put(DisplayOrder.RATE_RATIO, () -> new RateRatioR5(config, bundle));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhenR5(bundle, config.getLocale()));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_LIFETIME, () -> new MaxDosePerLifetimeR5(config, bundle));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, () -> new MaxDosePerAdministrationR5(config, bundle));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_PERIOD, () -> new MaxDosePerPeriodR5(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDurationR5(config, bundle));

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
