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
                        Map.entry(DisplayOrder.CODE, new TimingCode(config)),
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
                        Map.entry(DisplayOrder.BOUNDS_PERIOD, new BoundsPeriod(config))
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
