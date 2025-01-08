package jy95.fhir.common.translators;

import com.ibm.icu.text.MessageFormat;

import jy95.fhir.common.config.FDSConfig;
import jy95.fhir.common.types.AbstractTranslator;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractFrequencyFrequencyMax<C extends FDSConfig, D> extends AbstractTranslator<C, D> {

    // Translations
    protected final MessageFormat frequencyAndFrequencyMaxMsg;
    protected final MessageFormat frequencyMaxMsg;
    protected final MessageFormat frequencyMsg;

    public AbstractFrequencyFrequencyMax(C config) {
        super(config);
        var bundle = this.getResources();
        var locale = this.getConfig().getLocale();
        var msg1 = bundle.getString("fields.frequencyAndFrequencyMax");
        var msg2 = bundle.getString("fields.frequencyMax");
        var msg3 = bundle.getString("fields.frequency");
        frequencyAndFrequencyMaxMsg = new MessageFormat(msg1, locale);
        frequencyMaxMsg = new MessageFormat(msg2, locale);
        frequencyMsg = new MessageFormat(msg3, locale);
    }

    @Override
    public CompletableFuture<String> convert(D dosage) {
        return CompletableFuture.supplyAsync(() -> {

            var hasFrequencyFlag = hasFrequency(dosage);
            var hasFrequencyMaxFlag = hasFrequencyMax(dosage);
            var hasBoth = hasFrequencyFlag && hasFrequencyMaxFlag;

            if (hasBoth) {
                return turnFrequencyAndFrequencyMaxToString(dosage);
            }

            if (hasFrequencyMaxFlag) {
                return turnFrequencyMaxToString(dosage);
            }

            return turnFrequencyToString(dosage);
        });
    }

    protected String formatFrequencyAndFrequencyMaxText(int frequencyMin, int frequencyMax) {
        Map<String, Object> arguments = Map.of(
                "frequency", frequencyMin,
                "maxFrequency", frequencyMax
        );
        return frequencyAndFrequencyMaxMsg.format(arguments);
    }

    protected String formatFrequencyMaxText(int frequencyMax) {
        return frequencyMaxMsg.format(new Object[]{frequencyMax});
    }

    protected String formatFrequencyText(int frequency) {
        return frequencyMsg.format(new Object[]{frequency});
    }

    protected abstract boolean hasFrequency(D dosage);

    protected abstract boolean hasFrequencyMax(D dosage);

    protected abstract String turnFrequencyAndFrequencyMaxToString(D dosage);

    protected abstract String turnFrequencyMaxToString(D dosage);

    protected abstract String turnFrequencyToString(D dosage);
}
