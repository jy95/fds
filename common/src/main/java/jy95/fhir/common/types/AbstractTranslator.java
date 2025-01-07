package jy95.fhir.common.types;

import lombok.Getter;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.common.config.FDSConfig;

@Getter
public abstract class AbstractTranslator<T> {

    private final FDSConfig config;
    private final ResourceBundle resources;

    public AbstractTranslator(FDSConfig config) {
        this.config = config;
        this.resources = config.getSelectResourceBundle().apply(config.getLocale());
    }

    // To turn a dosage field into a human-representation
    public abstract CompletableFuture<String> convert(T dosage);

    // To see if a dosage field is present and thus being turned to String
    public boolean isPresent(T dosage) {
        return false;
    }
}
