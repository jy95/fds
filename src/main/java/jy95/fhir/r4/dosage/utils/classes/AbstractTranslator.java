package jy95.fhir.r4.dosage.utils.classes;

import jy95.fhir.r4.dosage.utils.miscellaneous.MultiResourceBundleControl;
import lombok.Getter;
import org.hl7.fhir.r4.model.Dosage;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

@Getter
public abstract class AbstractTranslator {

    private final FDUConfig config;
    private final MultiResourceBundleControl bundleControl;
    private final ResourceBundle resources;

    protected AbstractTranslator(FDUConfig config) {
        this.config = config;
        this.bundleControl = new MultiResourceBundleControl(
                "translations",
                "common",
                "daysOfWeek",
                "eventTiming",
                "quantityComparator",
                "unitsOfTime"
        );
        this.resources = ResourceBundle.getBundle(
                this.bundleControl.getBaseName(),
                this.config.getLocale(),
                this.bundleControl
        );
    }

    // To turn a dosage field into a human-representation
    public abstract CompletableFuture<String> convert(Dosage dosage);

    // To see if a dosage field is present and thus being turned to String
    public abstract boolean isPresent(Dosage dosage);
}