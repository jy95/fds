package jy95.fhir.common.types;

import lombok.Getter;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.common.config.FDSConfig;

@Getter
public abstract class AbstractTranslator<C extends FDSConfig, D> {

    private final C config;
    private final ResourceBundle resources;

    public AbstractTranslator(C config) {
        this.config = config;
        this.resources = config.getSelectResourceBundle().apply(config.getLocale());
    }

    // To turn a dosage field into a human-representation
    public abstract CompletableFuture<String> convert(D dosage);

    // To see if a dosage field is present and thus being turned to String
    public boolean isPresent(D dosage) {
        return false;
    }
}
