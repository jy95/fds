package jy95.fhir.r4.dosage.utils.classes;

import lombok.Getter;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;
import java.util.concurrent.CompletableFuture;

@Getter
public abstract class AbstractTranslator {
    
    private final DisplayOrder field;

    protected AbstractTranslator(DisplayOrder field) {
        this.field = field;
    }

    // To turn a dosage field into a human-representation
    public abstract CompletableFuture<String> convert(Dosage dosage);

    // To see if a dosage field is present and thus being turned to String
    public abstract boolean isPresent(Dosage dosage);
}