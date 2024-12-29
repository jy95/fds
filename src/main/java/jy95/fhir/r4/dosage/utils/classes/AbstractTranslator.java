package jy95.fhir.r4.dosage.utils.classes;

import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

public abstract class AbstractTranslator {

    // To turn a dosage field into a human-representation
    public abstract String convert(Dosage dosage);

    // To see if a dosage field is present and thus being turned to String
    public abstract boolean isPresent(Dosage dosage);

    // To debug with field this translator covers
    public abstract DisplayOrder getField();
}