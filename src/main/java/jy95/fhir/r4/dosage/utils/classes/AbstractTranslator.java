package jy95.fhir.r4.dosage.utils.classes;

import org.hl7.fhir.r4.model.Dosage;

public abstract class AbstractTranslator {
    public abstract String convert(Dosage dosage);
    public abstract boolean isPresent(Dosage dosage);
}
