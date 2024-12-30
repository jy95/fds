package jy95.fhir.r4.dosage.utils.miscellaneous;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import jy95.fhir.r4.dosage.utils.translators.*;

import java.util.EnumMap;
import java.util.Map;

public class Translators {
    private final Map<DisplayOrder, AbstractTranslator> translatorMap;

    public Translators(FDUConfig config) {
        this.translatorMap = new EnumMap<>(
                Map.of(
                        DisplayOrder.TEXT, new Text(config),
                        DisplayOrder.PATIENT_INSTRUCTION, new PatientInstruction(config),
                        DisplayOrder.DAY_OF_WEEK, new DayOfWeek(config),
                        DisplayOrder.TIME_OF_DAY, new TimeOfDay(config)
                )
        );
    }

    public AbstractTranslator getTranslator(DisplayOrder displayOrder) {
        return this.translatorMap.get(displayOrder);
    }
}
