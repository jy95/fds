package jy95.fhir.r4.dosage.utils.translators;

import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;

import java.util.concurrent.CompletableFuture;

public class Text extends AbstractTranslator {

    public Text(FDUConfig config){
        super(config);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(dosage::getText);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasText();
    }

}
