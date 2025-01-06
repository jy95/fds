package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FrequencyFrequencyMax extends AbstractTranslator {

    // Translations
    private final MessageFormat frequencyAndFrequencyMaxMsg;
    private final MessageFormat frequencyMaxMsg;
    private final MessageFormat frequencyMsg;

    public FrequencyFrequencyMax(FDUConfig config) {
        super(config);
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();
        var msg1 = bundle.getString("fields.frequencyAndFrequencyMax");
        var msg2 = bundle.getString("fields.frequencyMax");
        var msg3 = bundle.getString("fields.frequency");
        frequencyAndFrequencyMaxMsg = new MessageFormat(msg1, locale);
        frequencyMaxMsg = new MessageFormat(msg2, locale);
        frequencyMsg = new MessageFormat(msg3, locale);
    }

    @Override
    public CompletableFuture<String> convert(Dosage dosage) {
        return CompletableFuture.supplyAsync(() -> {
            var repeat = dosage.getTiming().getRepeat();

            var hasFrequency = repeat.hasFrequency();
            var hasFrequencyMax = repeat.hasFrequencyMax();
            var hasBoth = hasFrequency && hasFrequencyMax;

            if (hasBoth) {
                return turnFrequencyAndFrequencyMaxToString(dosage);
            }

            if (hasFrequencyMax) {
                return turnFrequencyMaxToString(dosage);
            }

            return turnFrequencyToString(dosage);
        });
    }

    @Override
    public boolean isPresent(Dosage dosage) {
        return dosage.hasTiming() && dosage.getTiming().hasRepeat()
                && (dosage.getTiming().getRepeat().hasFrequency() ||
                dosage.getTiming().getRepeat().hasFrequencyMax());
    }

    private String turnFrequencyAndFrequencyMaxToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var frequencyMin = repeat.getFrequency();
        var frequencyMax = repeat.getFrequencyMax();

        Map<String, Object> arguments1 = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );

        return frequencyAndFrequencyMaxMsg.format(arguments1);
    }

    private String turnFrequencyMaxToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var frequencyMax = repeat.getFrequencyMax();

        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    private String turnFrequencyToString(Dosage dosage) {

        var repeat = dosage.getTiming().getRepeat();
        var frequency = repeat.getFrequency();

        return frequencyMsg.format(new Object[]{frequency});
    }
}
