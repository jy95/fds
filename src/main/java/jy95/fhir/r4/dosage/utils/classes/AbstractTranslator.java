package jy95.fhir.r4.dosage.utils.classes;

import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

public abstract class AbstractTranslator {
    public abstract String convert(Dosage dosage);
    public abstract boolean isPresent(Dosage dosage);
    public abstract DisplayOrder getField();
}