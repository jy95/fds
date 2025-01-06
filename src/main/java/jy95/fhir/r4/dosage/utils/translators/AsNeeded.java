package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import jy95.fhir.r4.dosage.utils.functions.ListToString;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import org.hl7.fhir.r4.model.Dosage;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;

public class AsNeeded extends AbstractTranslator {

    // Translations
    private final MessageFormat asNeededForMsg;
    private final String asNeededMsg;

    public AsNeeded(FDUConfig config) {
        super(config);
        var bundle = getResources();
        var msg = bundle.getString("fields.asNeededFor");
        asNeededForMsg = new MessageFormat(msg, this.getConfig().getLocale());
        asNeededMsg = bundle.getString("fields.asNeeded");
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        var bundle = getResources();

        // Complex case - "as-need" for ...
        if (dosage.hasAsNeededCodeableConcept()) {
            var code = dosage.getAsNeededCodeableConcept();
            var codeAsText = this
                    .getConfig()
                    .getFromCodeableConceptToString()
                    .apply(code);

            return codeAsText
                    .thenApplyAsync(v -> ListToString.convert(bundle, List.of(v)))
                    .thenApplyAsync(v -> asNeededForMsg.format(new Object[]{v}));
        }

        // Simple case - only "as-needed"
        return CompletableFuture.supplyAsync(() -> asNeededMsg);
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        // No as-needed field(s) ? Not as-needed
        if (!dosage.hasAsNeeded()) {
            return false;
        }

        // Explicitly false ? Not as-needed
        // AsNeededCodeableConcept present ? As-needed
        return !dosage.hasAsNeededBooleanType() || dosage.getAsNeededBooleanType().booleanValue();
    }
}
