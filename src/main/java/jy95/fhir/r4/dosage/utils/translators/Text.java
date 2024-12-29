package jy95.fhir.r4.dosage.utils.translators;

import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.types.DisplayOrder;

import java.util.concurrent.CompletableFuture;

public class Text extends AbstractTranslator {

    public Text(){
        super(DisplayOrder.TEXT);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getText);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return !dosage.getText().isEmpty();
    }

}
