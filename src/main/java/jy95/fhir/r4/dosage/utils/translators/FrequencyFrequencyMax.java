package jy95.fhir.r4.dosage.utils.translators;

import com.ibm.icu.text.MessageFormat;
import jy95.fhir.r4.dosage.utils.classes.AbstractTranslator;
import jy95.fhir.r4.dosage.utils.config.FDUConfig;
import org.hl7.fhir.r4.model.Dosage;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class FrequencyFrequencyMax extends AbstractTranslator {

    public FrequencyFrequencyMax(FDUConfig config) {
        super(config);
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
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var frequencyMin = repeat.getFrequency();
        var frequencyMax = repeat.getFrequencyMax();

        var frequencyMsg = bundle.getString("fields.frequencyAndFrequencyMax");
        Map<String, Object> arguments1 = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );

        return new MessageFormat(frequencyMsg, locale).format(arguments1);
    }

    private String turnFrequencyMaxToString(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var frequencyMax = repeat.getFrequencyMax();

        var frequencyMsg = bundle.getString("fields.frequencyMax");
        return new MessageFormat(frequencyMsg, locale).format(new Object[]{frequencyMax});
    }

    private String turnFrequencyToString(Dosage dosage) {
        var locale = this.getConfig().getLocale();
        var bundle = this.getResources();

        var repeat = dosage.getTiming().getRepeat();
        var frequency = repeat.getFrequency();

        var frequencyMsg = bundle.getString("fields.frequency");
        return new MessageFormat(frequencyMsg, locale).format(new Object[]{frequency});
    }
}
