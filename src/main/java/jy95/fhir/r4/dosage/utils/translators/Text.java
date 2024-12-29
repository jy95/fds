package jy95.fhir.r4.dosage.utils.translators;

import lombok.Getter;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

public class Text extends AbstractTranslator {

    @Getter
    private final DisplayOrder field;

    public Text(){
        this.field = DisplayOrder.TEXT;
    }

    public String convert(Dosage dosage) {
        return dosage.getText();
    }

    public boolean isPresent(Dosage dosage) {
        return !dosage.getText().isEmpty();
    }

}
