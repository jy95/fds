package io.github.jy95.fds.r4.utils;

import io.github.jy95.fds.common.types.AbstractTranslatorsMap;
import io.github.jy95.fds.common.types.DisplayOrder;
import io.github.jy95.fds.common.types.Translator;
import io.github.jy95.fds.r4.config.FDSConfigR4;
import io.github.jy95.fds.r4.translators.*;
import org.hl7.fhir.r4.model.Dosage;

import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;
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
     * @param config The configuration object used for translation.
     * @param bundle a {@link java.util.ResourceBundle} object
     */
    public TranslatorsMapR4(FDSConfigR4 config, ResourceBundle bundle) {
        super(createTranslatorsSuppliers(config, bundle));
    }


    /**
     * Creates a map of {@link DisplayOrder} to {@link Supplier} instances that lazily initialize the corresponding
     * {@link Translator} objects for the R4 configuration.
     * <p>
     * This method ensures that translators are only created when needed, improving efficiency and reducing
     * unnecessary resource usage.It supports both simple translators and composite translators that depend
     * on other translators.
     * </p>
     *
     * @param config the {@link FDSConfigR4} configuration object used by the translators
     * @param bundle the {@link ResourceBundle} providing localized resources for the translators
     * @return a {@link Map} where the keys are {@link DisplayOrder} values and the values are {@link Supplier}
     *         instances that produce the corresponding {@link Translator} objects
     */
    private static Map<DisplayOrder, Supplier<Translator<FDSConfigR4, Dosage>>> createTranslatorsSuppliers(
            FDSConfigR4 config, ResourceBundle bundle) {
        EnumMap<DisplayOrder, Supplier<Translator<FDSConfigR4, Dosage>>> suppliers = new EnumMap<>(DisplayOrder.class);

        // All display order supported by R4 are initialized here
        suppliers.put(DisplayOrder.TEXT, TextR4::new);
        suppliers.put(DisplayOrder.PATIENT_INSTRUCTION, PatientInstructionR4::new);
        suppliers.put(DisplayOrder.DAY_OF_WEEK, () -> new DayOfWeekR4(config, bundle));
        suppliers.put(DisplayOrder.TIME_OF_DAY, () -> new TimeOfDayR4(config, bundle));
        suppliers.put(DisplayOrder.TIMING_CODE, () -> new TimingCodeR4(config));
        suppliers.put(DisplayOrder.TIMING_EVENT, () -> new TimingEventR4(config, bundle));
        suppliers.put(DisplayOrder.METHOD, () -> new MethodR4(config));
        suppliers.put(DisplayOrder.ROUTE, () -> new RouteR4(config));
        suppliers.put(DisplayOrder.SITE, () -> new SiteR4(config));
        suppliers.put(DisplayOrder.EXTENSION, () -> new ExtensionR4(config));
        suppliers.put(DisplayOrder.TIMING_EXTENSION, () -> new TimingExtensionR4(config));
        suppliers.put(DisplayOrder.TIMING_REPEAT_EXTENSION, () -> new TimingRepeatExtensionR4(config));
        suppliers.put(DisplayOrder.MODIFIER_EXTENSION, () -> new ModifierExtensionR4(config));
        suppliers.put(DisplayOrder.TIMING_MODIFIER_EXTENSION, () -> new TimingModifierExtensionR4(config));
        suppliers.put(DisplayOrder.ADDITIONAL_INSTRUCTION, () -> new AdditionalInstructionR4(config, bundle));
        suppliers.put(DisplayOrder.AS_NEEDED, () -> new AsNeededR4(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_PERIOD, () -> new BoundsPeriodR4(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_RANGE, () -> new BoundsRangeR4(config, bundle));
        suppliers.put(DisplayOrder.DURATION_DURATION_MAX, () -> new DurationDurationMaxR4(config, bundle));
        suppliers.put(DisplayOrder.FREQUENCY_FREQUENCY_MAX, () -> new FrequencyFrequencyMaxR4(config, bundle));
        suppliers.put(DisplayOrder.PERIOD_PERIOD_MAX, () -> new PeriodPeriodMaxR4(config, bundle));
        suppliers.put(DisplayOrder.COUNT_COUNT_MAX, () -> new CountCountMaxR4(config, bundle));
        suppliers.put(DisplayOrder.DOSE_QUANTITY, () -> new DoseQuantityR4(config, bundle));
        suppliers.put(DisplayOrder.DOSE_RANGE, () -> new DoseRangeR4(config, bundle));
        suppliers.put(DisplayOrder.RATE_QUANTITY, () -> new RateQuantityR4(config, bundle));
        suppliers.put(DisplayOrder.RATE_RANGE, () -> new RateRangeR4(config, bundle));
        suppliers.put(DisplayOrder.RATE_RATIO, () -> new RateRatioR4(config, bundle));
        suppliers.put(DisplayOrder.OFFSET_WHEN, () -> new OffsetWhenR4(bundle, config.getLocale()));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_LIFETIME, () -> new MaxDosePerLifetimeR4(config, bundle));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_ADMINISTRATION, () -> new MaxDosePerAdministrationR4(config, bundle));
        suppliers.put(DisplayOrder.MAX_DOSE_PER_PERIOD, () -> new MaxDosePerPeriodR4(config, bundle));
        suppliers.put(DisplayOrder.BOUNDS_DURATION, () -> new BoundsDurationR4(config, bundle));

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
